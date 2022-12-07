package com.ixxus.etram.confluence.infrastructure.rest;

import com.ixxus.etram.confluence.application.services.ConfluenceService;
import com.ixxus.etram.confluence.model.entity.Article;
import com.ixxus.etram.confluence.model.entity.ChildArticle;
import com.ixxus.etram.confluence.model.entity.Space;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/confluence")
@RequiredArgsConstructor
public class ConfluenceController {

    private final ConfluenceService confluenceService;

    // Create a new wiki article in a given space. curl example:
    // https://developer.atlassian.com/server/confluence/confluence-rest-api-examples/#create-a-new-page
    // curl -u admin:admin -X POST -H 'Content-Type: application/json' -d '{"type":"page","title":"new page", "space":{"key":"TST"},"body":{"storage":{"value":"<p>This is <br/> a new page</p>","representation": "storage"}}}' http://localhost:8080/confluence/rest/api/content/
    @PostMapping("/article")
    public ResponseEntity<?> createArticle(@RequestBody Article article) {
        return confluenceService.createArticle(article);
    }

    // Create a new wiki article under a given article. curl example:
    // https://developer.atlassian.com/server/confluence/confluence-rest-api-examples/#create-a-new-page-as-a-child-of-another-page
    // curl -u admin:admin -X POST -H 'Content-Type: application/json' -d '{"type":"page","title":"new page", "ancestors":[{"id":456}], "space":{"key":"TST"},"body":{"storage":{"value": "<p>This is a new page</p>","representation":"storage"}}}' http://localhost:8080/confluence/rest/api/content/
    @PostMapping("/article")
    public ResponseEntity<?> createChildArticle(@RequestBody ChildArticle childArticle) {
        return confluenceService.createChildArticle(childArticle);
    }

    // Update the content of an article. curl example:
    // https://developer.atlassian.com/server/confluence/confluence-rest-api-examples/#update-a-page
    // curl -u admin:admin -X PUT -H 'Content-Type: application/json' -d '{"id":"3604482","type":"page", "title":"new page","space":{"key":"TST"},"body":{"storage":{"value": "<p>This is the updated text for the new page</p>","representation":"storage"}}, "version":{"number":2}}' http://localhost:8080/confluence/rest/api/content/3604482
    @PutMapping("/article")
    public ResponseEntity<?> updateArticle(@RequestBody Article article) {
        return confluenceService.updateArticle(article);
    }

    // Attach new images to the article. curl example:
    // curl -v -S -u admin:admin -X POST -H "X-Atlassian-Token: no-check" -F "file=@myfile.txt" -F "comment=this is my file" "http://localhost:8080/confluence/rest/api/content/3604482/child/attachment"
    @PostMapping("/article/{articleId}/attachment")
    public ResponseEntity<?> uploadAttachment(@PathVariable String articleId, @RequestParam("file") MultipartFile file, @RequestParam("comment") String comment) {
        return confluenceService.uploadAttachment(articleId, file, comment);
    }

    // Create space. curl example:
    // https://developer.atlassian.com/server/confluence/confluence-rest-api-examples/#create-a-space
    // curl -u admin:admin -X POST -H 'Content-Type: application/json' -d' { "key":"RAID", "name":"Raider", "type":"global",  "description":{"plain": { "value": "Raider Space for raiders","representation": "plain" }}}' http://localhost:8080/confluence/rest/api/space
    @PostMapping("/space")
    public ResponseEntity<?> createSpace(@RequestBody Space space) {
        return confluenceService.createSpace(space);
    }

}
