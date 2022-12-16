/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.application.services;

import com.ixxus.etram.experttrack.infrastructure.db.repository.project.CustomProjectRepository;
import com.ixxus.etram.experttrack.model.ArticleTopLevel;
import com.ixxus.etram.experttrack.model.ProjectToc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final CustomProjectRepository projectRepository;

    public List<ArticleTopLevel> getTopArticles(Integer idProject) {

        var customTopArticleEntityList = projectRepository.findTopArticlesProject(idProject);

        return customTopArticleEntityList.stream().map(x -> ArticleTopLevel.builder()
                .idPageParent(x.getIdPageParent())
                .pageName(x.getPageName())
                .build()).toList();
    }

    public List<ProjectToc> getToCProject(Integer projectId) {

        var customTocProjectEntityList = projectRepository.findProjectToc(projectId);

        return customTocProjectEntityList.stream().map(x -> ProjectToc.builder()
                .idPage(x.getIdPage())
                .pageName(x.getPageName())
                .idPageToc(x.getIdPageToc())
                .isToc(x.isToc())
                .tocSequence(x.getTocSequence())
                .build()).toList();

    }
}
