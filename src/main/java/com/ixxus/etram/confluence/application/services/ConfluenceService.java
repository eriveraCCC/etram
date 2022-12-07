package com.ixxus.etram.confluence.application.services;

import com.ixxus.etram.confluence.model.entity.Article;
import com.ixxus.etram.confluence.model.entity.ChildArticle;
import com.ixxus.etram.confluence.model.entity.Space;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConfluenceService {

    public ResponseEntity<?> createArticle(Article article) {
        return null;
    }

    public ResponseEntity<?> createChildArticle(ChildArticle childArticle) {
        return null;
    }

    public ResponseEntity<?> updateArticle(Article article) {
        return null;
    }

    public ResponseEntity<?> uploadAttachment(String articleId, MultipartFile file, String comment) {
        return null;
    }

    public ResponseEntity<?> createSpace(Space space) {
        return null;
    }
}
