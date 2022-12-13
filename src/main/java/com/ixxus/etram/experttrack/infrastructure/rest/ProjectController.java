/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.infrastructure.rest;

import com.ixxus.etram.experttrack.application.services.ProjectService;
import com.ixxus.etram.experttrack.model.ArticleTopLevel;
import com.ixxus.etram.experttrack.model.ProjectToc;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{projectId}/top")
    public ResponseEntity<List<ArticleTopLevel>> getTopArticles(@PathVariable Integer projectId) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getTopArticles(projectId));
    }

    @GetMapping("/{projectId}/toc")
    public ResponseEntity<List<ProjectToc>> getToCProject(@PathVariable Integer projectId) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getToCProject(projectId));
    }


}
