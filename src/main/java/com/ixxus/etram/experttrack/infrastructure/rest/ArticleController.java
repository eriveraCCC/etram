/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.infrastructure.rest;

import com.ixxus.etram.experttrack.application.services.ArticleService;
import com.ixxus.etram.experttrack.model.ArticleChild;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    //TODO check if projectId is needed in path
    @GetMapping("/{articleId}/child")
    public ResponseEntity<List<ArticleChild>> getChildArticles(@PathVariable Integer articleId) {

        return ResponseEntity.status(HttpStatus.OK).body(articleService.getChildArticles(articleId));
    }

    @GetMapping("/{articleId}/html")
    public ResponseEntity<?> getHTMLContent(@PathVariable Integer articleId) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getHTMLContent(articleId));
    }


    @GetMapping(value = "/{articleId}/images", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getImagesFromArticle(@PathVariable Integer articleId) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "images.zip");

        var compressedImages = articleService.getImagesFromArticle(articleId);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(new ByteArrayResource(compressedImages));
    }

}
