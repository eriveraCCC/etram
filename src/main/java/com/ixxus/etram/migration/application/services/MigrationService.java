package com.ixxus.etram.migration.application.services;

import com.ixxus.etram.confluence.application.services.ConfluenceService;
import com.ixxus.etram.confluence.infrastructure.rest.out.ConfluenceRestService;
import com.ixxus.etram.confluence.infrastructure.rest.out.response.ConfluenceRestResponse;
import com.ixxus.etram.confluence.model.*;
import com.ixxus.etram.experttrack.application.services.ArticleService;
import com.ixxus.etram.experttrack.application.services.ProjectService;
import com.ixxus.etram.experttrack.model.ArticleChild;
import com.ixxus.etram.experttrack.model.ArticleHtmlContent;
import com.ixxus.etram.experttrack.model.ArticleTopLevel;
import com.ixxus.etram.experttrack.model.ProjectToc;
import com.ixxus.etram.migration.application.services.dto.ReferencedPage;
import com.ixxus.etram.migration.model.entity.CreatedArticle;
import com.ixxus.etram.migration.model.entity.CreatedChildArticle;
import com.ixxus.etram.migration.model.entity.ProjectHierarchy;
import com.ixxus.etram.migration.model.entity.ProjectHierarchyTopArticle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MigrationService {


    private final ProjectService projectService;

    private final ArticleService articleService;

    private final ConfluenceService confluenceService;

    private final ConfluenceRestService confluenceRestService;

    public List<CreatedArticle> migrate(Integer projectId, String space) {

        log.info("Migrating to confluence, project: {}", projectId);

        var projectHierarchy = getProjectHierachy(projectId);
        addUnlinkedProjects(projectHierarchy, projectId);

        return createArticlesConfluence(projectHierarchy, space);
    }


    public void fixBrokenLinks(Integer projectId, String space) {

        var confluenceArticleList = confluenceService.getAllArticles(space);

        for (ConfluenceRestResponse confluenceArticle : confluenceArticleList.getResults()) {

            var references = getLinksFromHref(confluenceArticle);

            if (!references.isEmpty())
                for (ReferencedPage referencedPage : references) {

                    log.info(referencedPage.toString());

                    var pageName = articleService.getPageName(referencedPage.getPageId());

                    log.info("Searching by page name: {}", pageName);

                    var article = confluenceArticleList.getResults().stream()
                            .filter(art -> art.getTitle().equalsIgnoreCase(pageName)).findFirst();

                    if (article.isPresent()) {

                        log.info("Found article: {}", article.get().getTitle());

                        var link = "/wiki" + article.get().getLinks().getWebui();

                        referencedPage.setNewLink(link);

                        log.info("WITH NEW LINK :" + referencedPage);
                    }
                }
            if (!references.isEmpty()) {
                var updatedContent = changeLinks(confluenceArticle, references);
                var response = updateArticle(confluenceArticle, updatedContent, space);
                log.info(response.toString());
            }
        }
    }

    private String changeLinks(ConfluenceRestResponse confluenceArticle, List<ReferencedPage> referencedPages) {

        log.info("Update article: {}", confluenceArticle.getTitle());
        log.info("Referenced pages: {}", referencedPages.size());

        var content = confluenceArticle.getBody().getStorage().getValue();
        var parsedContent = parseHtmlContent(content);

        for (ReferencedPage referencedPage : referencedPages) {

            if (referencedPage.getFullLink() != null && referencedPage.getNewLink() != null) {

                log.info("Replacing link {} with {}, PAGE: {}", referencedPage.getFullLink(), referencedPage.getNewLink(), confluenceArticle.getTitle());
                parsedContent = parsedContent.replace(referencedPage.getFullLink(), referencedPage.getNewLink());
                log.info(referencedPage.toString());
            }
        }

        return parsedContent;
    }

    private ConfluenceRestResponse updateArticle(ConfluenceRestResponse confluenceArticle, String content, String space) {

        log.info("UPDATING ARTICLE: {}", confluenceArticle.getTitle());


        Article article = Article.builder()
                .id(confluenceArticle.getId())
                .type(confluenceArticle.getType())
                .title(confluenceArticle.getTitle())
                .space(Space.builder()
                        .key(space)
                        .build())
                .body(Body.builder()
                        .storage(Storage.builder()
                                .value(content)
                                .representation("storage")
                                .build())
                        .build())
                .version(Version.builder()
                        .number(confluenceArticle.getVersion().getNumber() + 1)
                        .build())
                .build();

        log.info("-------------------");
        log.info("Article: {}", article.getTitle());
        log.info("-------------------");

        return confluenceRestService.updateArticle(article);

    }

    private List<ReferencedPage> getLinksFromHref(ConfluenceRestResponse confluenceArticle) {

        List<ReferencedPage> referencedPages = new ArrayList<>();
        var html = parseHtmlContent(confluenceArticle.getBody().getStorage().getValue());

        Pattern pattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1");
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            String href = matcher.group(2);

            String[] params = href.split("&");
            Map<String, String> map = new HashMap<>();
            for (String param : params) {
                if (params.length >= 2) {
                    String name = param.split("=")[0];
                    String value = param.split("=")[1];
                    map.put(name, value);
                }
            }

            if (map.get("amp;selectedIdProject") != null && map.get("amp;idPage") != null) {
                String param1 = map.get("amp;selectedIdProject");
                String param2 = map.get("amp;idPage");

                referencedPages.add(ReferencedPage.builder()
                        .fullLink(href)
                        .projectId(Integer.valueOf(param1))
                        .pageId(Integer.valueOf(param2))
                        .build());
            }
        }

        return referencedPages;
    }

    private String parseHtmlContent(String html) {

        return html.replace("\\", "");

    }

    private ProjectHierarchy getProjectHierachy(Integer projectId) {

        ProjectHierarchy projectHierarchy = new ProjectHierarchy();
        List<ProjectHierarchyTopArticle> hierarchyTopArticles = new ArrayList<>();
        List<ProjectToc> tableOfContent = projectService.getToCProject(projectId);

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

        return projectHierarchy;

    }

    private void addUnlinkedProjects(ProjectHierarchy projectHierarchy, Integer projectId) {

        var linkedArticles = new ArrayList<Integer>();

        for (ProjectHierarchyTopArticle articleTopLevel : projectHierarchy.getProjectTopArticles()) {
            linkedArticles.add(articleTopLevel.getArticleTopLevel().getIdPageParent());
            linkedArticles.addAll(articleTopLevel.getChildArticles().stream().map(ArticleChild::getIdPageChild).toList());
        }

        var unlinkedArticles = articleService.getUnlinkedArticles(projectId, linkedArticles);

        var unlinkedPagesTopArticle = ProjectHierarchyTopArticle.builder()
                .articleTopLevel(ArticleTopLevel.builder()
                        .idPageParent(null)
                        .pageName("Unlinked articles")
                        .build())
                .childArticles(unlinkedArticles)
                .build();

        projectHierarchy.getProjectTopArticles().add(unlinkedPagesTopArticle);

    }

    private List<CreatedArticle> createArticlesConfluence(ProjectHierarchy projectHierarchy, String space) {

        List<CreatedArticle> createdArticlesList = new ArrayList<>();

        // Create top level articles
        for (ProjectHierarchyTopArticle hierarchyTopArticle : projectHierarchy.getProjectTopArticles()) {

            try {
                var createdArticle = createArticle(hierarchyTopArticle.getArticleTopLevel(), space);

                List<CreatedChildArticle> createdChildArticlesList = new ArrayList<>();
                for (ArticleChild articleChild : hierarchyTopArticle.getChildArticles()) {
                    try {
                        var createdChildArticle = createChildArticles(articleChild, createdArticle.getId(), space);
                        createdChildArticlesList.add(CreatedChildArticle.builder()
                                .confluenceId(createdChildArticle.getId())
                                .parentConfluenceId(createdArticle.getId())
                                .title(createdChildArticle.getTitle())
                                .build());
                    } catch (HttpClientErrorException e) {
                        log.error("Child article could not be created", e);
                    }
                }

                createdArticlesList.add(CreatedArticle.builder()
                        .confluenceId(createdArticle.getId())
                        .title(createdArticle.getTitle())
                        .childArticles(createdChildArticlesList)
                        .build());

            } catch (HttpClientErrorException e) {

                log.error("Article could not be created", e);

            }
        }


        return createdArticlesList;
    }

    private ConfluenceRestResponse createArticle(ArticleTopLevel articleTopLevel, String space) {

        log.info("Migrating to confluence, parent article: {}", articleTopLevel.getPageName());

        var articleHtmlContent = new ArticleHtmlContent();

        if (articleTopLevel.getIdPageParent() != null) {
            articleHtmlContent = articleService.getHTMLContent(articleTopLevel.getIdPageParent());
        } else {
            articleHtmlContent.setContent("<p>Unlinked pages parent</p>");
        }


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


        return confluenceService.createArticle(article);
    }

    private ConfluenceRestResponse createChildArticles(ArticleChild articleChild, String parentId, String space) {

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

        return confluenceService.createChildArticle(childArticle);

    }

}
