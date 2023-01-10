package com.ixxus.etram.migration.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class ProjectHierarchy {

    private Integer projectId;

    private List<ProjectHierarchyTopArticle> projectTopArticles;

}
