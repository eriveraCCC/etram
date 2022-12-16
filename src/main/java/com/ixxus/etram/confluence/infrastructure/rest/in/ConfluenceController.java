/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.infrastructure.rest.in;

import com.ixxus.etram.confluence.application.services.ConfluenceService;
import com.ixxus.etram.confluence.infrastructure.rest.in.article.request.CreateArticleRequest;
import com.ixxus.etram.confluence.infrastructure.rest.in.article.request.CreateChildArticleRequest;
import com.ixxus.etram.confluence.infrastructure.rest.in.article.request.UpdateArticleRequest;
import com.ixxus.etram.confluence.infrastructure.rest.in.space.request.CreateSpaceRequest;
import com.ixxus.etram.confluence.infrastructure.rest.out.response.ConfluenceRestResponse;
import com.ixxus.etram.confluence.infrastructure.rest.out.response.ConfluenceRestResponseList;
import com.ixxus.etram.confluence.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/confluence")
@RequiredArgsConstructor
public class ConfluenceController {

    private final ConfluenceService confluenceService;

    @PostMapping("/article")
    public ResponseEntity<ConfluenceRestResponse> createArticle(@RequestBody CreateArticleRequest articleRequest) {

        Article article = Article.builder()
                .type(articleRequest.getType())
                .title(articleRequest.getTitle())
                .space(Space.builder()
                        .key(articleRequest.getSpace().getKey())
                        .build())
                .body(Body.builder()
                        .storage(Storage.builder()
                                .value(articleRequest.getBody().getStorage().getValue())
                                .representation(articleRequest.getBody().getStorage().getRepresentation())
                                .build())
                        .build())
                .build();

        var response = confluenceService.createArticle(article);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/article/child")
    public ResponseEntity<ConfluenceRestResponse> createChildArticle(@RequestBody CreateChildArticleRequest childArticleRequest) {

        ChildArticle childArticle = ChildArticle.builder()
                .type(childArticleRequest.getType())
                .title(childArticleRequest.getTitle())
                .ancestors(childArticleRequest.getAncestors().stream().map(x -> Ancestor.builder()
                        .id(x.getId())
                        .build()).toList())
                .space(Space.builder()
                        .key(childArticleRequest.getSpace().getKey())
                        .build())
                .body(Body.builder()
                        .storage(Storage.builder()
                                .value(childArticleRequest.getBody().getStorage().getValue())
                                .representation(childArticleRequest.getBody().getStorage().getRepresentation())
                                .build())
                        .build())
                .build();

        var response = confluenceService.createChildArticle(childArticle);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/article")
    public ResponseEntity<ConfluenceRestResponse> updateArticle(@RequestBody UpdateArticleRequest articleRequest) {

        Article article = Article.builder()
                .id(articleRequest.getId())
                .type(articleRequest.getType())
                .title(articleRequest.getTitle())
                .space(Space.builder()
                        .key(articleRequest.getSpace().getKey())
                        .build())
                .body(Body.builder()
                        .storage(Storage.builder()
                                .value(articleRequest.getBody().getStorage().getValue())
                                .representation(articleRequest.getBody().getStorage().getRepresentation())
                                .build())
                        .build())
                .version(Version.builder()
                        .number(articleRequest.getVersion().getNumber())
                        .build())
                .build();

        var response = confluenceService.updateArticle(article);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/article/{articleId}/attachment")
    public ResponseEntity<ConfluenceRestResponseList> uploadAttachment(@PathVariable String articleId, @RequestParam("file") MultipartFile file, @RequestParam("comment") String comment) throws IOException {
        var response =  confluenceService.uploadAttachment(articleId, file, comment);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/space")
    public ResponseEntity<ConfluenceRestResponse> createSpace(@RequestBody CreateSpaceRequest spaceRequest) {

        Space space = Space.builder()
                .key(spaceRequest.getKey())
                .name(spaceRequest.getName())
                .type(spaceRequest.getType())
                .description(Description.builder()
                        .plain(Storage.builder()
                                .representation(spaceRequest.getDescription().getPlain().getRepresentation())
                                .value(spaceRequest.getDescription().getPlain().getValue())
                                .build())
                        .build())
                .build();

        var response = confluenceService.createSpace(space);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
