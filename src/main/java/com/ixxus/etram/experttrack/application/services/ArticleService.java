/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.application.services;

import com.ixxus.etram.experttrack.infrastructure.db.TblCollectionRepository;
import com.ixxus.etram.experttrack.infrastructure.rest.dto.GetImageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final TblCollectionRepository tblCollectionRepository;

    public ResponseEntity<?> getChildArticles(String articleId) {

        var entities = tblCollectionRepository.findAll();

        log.info(entities.toString());

        return null;
    }

    public ResponseEntity<?> getHTMLContent(String articleId) {
        // call repository to get content from an article id

        // check if returns a HTML content, if not, convert content to HTML with some plugin? I guess

        return null;
    }

    public ResponseEntity<?> getToCArticle(String articleId) {
        // return toc object

        return null;
    }

    public ResponseEntity<?> getTopArticles(GetImageRequest url) {
        // guess is parent articles, so get parent articles from project

        // build object to return Map<String id, String title> ---> tentative

        return null;
    }
}
