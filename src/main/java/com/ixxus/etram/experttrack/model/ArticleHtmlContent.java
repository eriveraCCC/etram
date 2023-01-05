/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleHtmlContent {

    private Integer idPage;

    private Integer idContent;

    private String pageName;

    private String content;

}
