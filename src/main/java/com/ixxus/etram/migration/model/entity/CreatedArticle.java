package com.ixxus.etram.migration.model.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreatedArticle {

    private final String confluenceId;

    private final String title;

    private final List<CreatedChildArticle> childArticles;

}
