/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.infrastructure.rest.in.article.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateArticleRequest {

    private String type;
    private String title;
    private CreateArticleSpaceRequest space;
    private CreateArticleBodyRequest body;

}
