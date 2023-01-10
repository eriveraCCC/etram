package com.ixxus.etram.migration.model.entity;

import com.ixxus.etram.experttrack.model.ArticleChild;
import com.ixxus.etram.experttrack.model.ArticleTopLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
public class ProjectHierarchyTopArticle {

    private final ArticleTopLevel articleTopLevel;

    private final List<ArticleChild> childArticles;

}
