/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleHtmlContent {

    private Integer idPage;

    private Integer idContent;

    private String pageName;

    private String content;

}
