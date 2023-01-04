/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.application.services;

import com.ixxus.etram.confluence.infrastructure.rest.out.ConfluenceRestService;
import com.ixxus.etram.confluence.infrastructure.rest.out.response.ConfluenceRestResponse;
import com.ixxus.etram.confluence.infrastructure.rest.out.response.ConfluenceRestResponseList;
import com.ixxus.etram.confluence.model.Article;
import com.ixxus.etram.confluence.model.ChildArticle;
import com.ixxus.etram.confluence.model.Space;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ConfluenceService {

    private final ConfluenceRestService confluenceRestService;

    public ConfluenceRestResponse getArticle(String articleId) {
        return confluenceRestService.getArticleContent(articleId);
    }

    public ConfluenceRestResponseList getAllArticles(String spaceId) {
        return confluenceRestService.getAllArticles(spaceId);
    }
    public ConfluenceRestResponse createArticle(Article article) {
        return confluenceRestService.createArticle(article);
    }

    public ConfluenceRestResponse createChildArticle(ChildArticle childArticle) {
        return confluenceRestService.createChildArticle(childArticle);
    }

    public ConfluenceRestResponse updateArticle(Article article) {
        return confluenceRestService.updateArticle(article);
    }

    public ConfluenceRestResponseList uploadAttachment(String articleId, MultipartFile file, String comment) throws IOException {
        return confluenceRestService.uploadAttachments(articleId, file, comment);
    }

    public ConfluenceRestResponse createSpace(Space space) {
        return confluenceRestService.createSpace(space);
    }
}
