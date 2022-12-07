package com.ixxus.etram.experttrack.infrastructure.rest;

import com.ixxus.etram.experttrack.application.services.ArticleService;
import com.ixxus.etram.experttrack.infrastructure.rest.dto.GetImageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    //TODO check if projectId is needed in path
    @GetMapping("/{articleId}/child")
    public ResponseEntity<?> getChildArticles(@PathVariable String articleId) {
        return articleService.getChildArticles(articleId);
    }

    @GetMapping("/{articleId}/html")
    public ResponseEntity<?> getHTMLContent(@PathVariable String articleId) {
        return articleService.getHTMLContent(articleId);
    }

    @GetMapping("/{articleId}/toc")
    public ResponseEntity<?> getToCArticle(@PathVariable String articleId) {
        return articleService.getToCArticle(articleId);
    }

    @GetMapping("/image")
    public ResponseEntity<?> getImage(@RequestBody GetImageRequest getImageRequest) {
        return articleService.getTopArticles(getImageRequest);
    }

}
