package com.ixxus.etram.migration.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatedChildArticle {

    private final String confluenceId;

    private final String parentConfluenceId;

    private final String title;

}
