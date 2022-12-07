package com.ixxus.etram.experttrack.application.services;

import com.ixxus.etram.experttrack.infrastructure.db.ArticleRepository;
import com.ixxus.etram.experttrack.infrastructure.rest.dto.GetImageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ResponseEntity<?> getChildArticles(String articleId) {
        // call repository with custom query for child articles

        // build object to return Map<String id, String title> ---> tentative

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