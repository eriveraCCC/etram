package com.ixxus.etram.experttrack.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @GetMapping("/{projectId}/top")
    public ResponseEntity<?> getTopArticles() {
        return null;
    }

    @GetMapping("/{projectId}/toc")
    public ResponseEntity<?> getToCProject() {
        return null;
    }


}
