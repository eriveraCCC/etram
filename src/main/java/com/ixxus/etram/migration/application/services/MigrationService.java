package com.ixxus.etram.migration.application.services;

import com.ixxus.etram.confluence.application.services.ConfluenceService;
import com.ixxus.etram.confluence.model.*;
import com.ixxus.etram.experttrack.application.services.ArticleService;
import com.ixxus.etram.experttrack.application.services.ProjectService;
import com.ixxus.etram.experttrack.model.ArticleChild;
import com.ixxus.etram.experttrack.model.ArticleTopLevel;
import com.ixxus.etram.experttrack.model.ProjectToc;
import com.ixxus.etram.migration.model.entity.ProjectHierarchy;
import com.ixxus.etram.migration.model.entity.ProjectHierarchyTopArticle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MigrationService {


    private final ProjectService projectService;

    private final ArticleService articleService;

    private final ConfluenceService confluenceService;

    public ResponseEntity<?> migrate(Integer projectId, String space) {

        log.info("Migrating to confluence, project: {}", projectId);

        // Object to be returned
        ProjectHierarchy projectHierarchy = new ProjectHierarchy();
        List<ProjectHierarchyTopArticle> hierarchyTopArticles = new ArrayList<>();

        List<ProjectToc> tableOfContent = projectService.getToCProject(projectId);
        //List<ArticleTopLevel> topLevelArticles = projectService.getTopArticles(projectId);

        for (ProjectToc topLevelArticle : tableOfContent) {

            List<ArticleChild> childArticlesList = articleService.getChildArticles(topLevelArticle.getIdPage());

            var projectHierarchyTopArticle = ProjectHierarchyTopArticle.builder()
                    .articleTopLevel(ArticleTopLevel.builder()
                            .idPageParent(topLevelArticle.getIdPage())
                            .pageName(topLevelArticle.getPageName())
                            .build())
                    .childArticles(childArticlesList)
                    .build();

            hierarchyTopArticles.add(projectHierarchyTopArticle);
        }

        projectHierarchy.setProjectId(projectId);
        projectHierarchy.setProjectTopArticles(hierarchyTopArticles);

        createArticlesConfluence(projectHierarchy, space);

        return ResponseEntity.ok().body(projectHierarchy);

    }

    private void createArticlesConfluence (ProjectHierarchy projectHierarchy, String space) {

        // Create top level articles
        for (ProjectHierarchyTopArticle hierarchyTopArticle : projectHierarchy.getProjectTopArticles()) {
            var confluenceId = createArticle(hierarchyTopArticle.getArticleTopLevel(), space);

            for (ArticleChild articleChild : hierarchyTopArticle.getChildArticles()) {
                createChildArticles(articleChild, confluenceId, space);
            }

        }
    }

    private String createArticle (ArticleTopLevel articleTopLevel, String space) {

        log.info("Migrating to confluence, parent article: {}", articleTopLevel.getPageName());

        var articleHtmlContent = articleService.getHTMLContent(articleTopLevel.getIdPageParent());

        var article = Article.builder()
                .type("page")
                .title(articleTopLevel.getPageName())
                .space(Space.builder()
                        .key(space)
                        .build())
                .body(Body.builder()
                        .storage(Storage.builder()
                                .value(articleHtmlContent.getContent())
                                .representation("storage")
                                .build())
                        .build())
                .build();


        var response = confluenceService.createArticle(article);

        return response.getId();
    }

    private void createChildArticles(ArticleChild articleChild, String parentId, String space) {

        log.info("Migrating to confluence, child article: {}, parentId: {}", articleChild.getPageNameChild(), parentId);

        var childArticleHtmlContent = articleService.getHTMLContent(articleChild.getIdPageChild());

        ChildArticle childArticle = ChildArticle.builder()
                .type("page")
                .title(articleChild.getPageNameChild())
                .ancestors(Collections.singletonList(Ancestor.builder()
                                .id(parentId)
                        .build()))
                .space(Space.builder()
                        .key(space)
                        .build())
                .body(Body.builder()
                        .storage(Storage.builder()
                                .value(childArticleHtmlContent.getContent())
                                .representation("storage")
                                .build())
                        .build())
                .build();

        var response = confluenceService.createChildArticle(childArticle);

    }

}
