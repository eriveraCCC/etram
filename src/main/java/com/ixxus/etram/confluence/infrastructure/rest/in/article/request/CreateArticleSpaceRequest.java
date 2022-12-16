/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.infrastructure.rest.in.article.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateArticleSpaceRequest {

    private String key;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CreateArticleDescriptionRequest description;

}
