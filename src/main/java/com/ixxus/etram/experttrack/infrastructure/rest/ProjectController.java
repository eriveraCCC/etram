/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.infrastructure.rest;

import com.ixxus.etram.experttrack.application.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{projectId}/top")
    public ResponseEntity<?> getTopArticles(@PathVariable String projectId) {
        return projectService.getTopArticles(projectId);
    }

    @GetMapping("/{projectId}/toc")
    public ResponseEntity<?> getToCProject(@PathVariable String projectId) {
        return projectService.getToCProject(projectId);
    }


}
