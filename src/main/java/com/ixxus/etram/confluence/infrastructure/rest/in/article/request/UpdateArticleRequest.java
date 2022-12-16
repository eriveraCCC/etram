/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.infrastructure.rest.in.article.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateArticleRequest extends CreateArticleRequest{

    private String id;

    private UpdateArticleVersionRequest version;
}