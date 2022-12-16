/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.infrastructure.rest.in.article.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CreateChildArticleRequest extends CreateArticleRequest {

    private List<CreateChildArticleAncestorRequest> ancestors;

}
