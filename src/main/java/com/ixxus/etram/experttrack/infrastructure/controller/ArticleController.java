package com.ixxus.etram.experttrack.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {


    //TODO check if projectId is needed in path
    @GetMapping("/{articleId}/child")
    public ResponseEntity<?> getChildArticles(@PathVariable String articleId) {
        return null;
    }

    @GetMapping("/{articleId}/html")
    public ResponseEntity<?> getHTMLContent(@PathVariable String articleId) {
        return null;
    }

    @GetMapping("/{articleId}/toc")
    public ResponseEntity<?> getToCArticle() {
        return null;
    }

    @GetMapping("/{articleId}/image")
    public ResponseEntity<?> getTopArticles(@RequestBody String url) {
        return null;
    }

}
