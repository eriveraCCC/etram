package com.ixxus.etram.experttrack.application.services;

import com.ixxus.etram.experttrack.infrastructure.db.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ResponseEntity<?> getTopArticles(String projectId) {
        // build object to return Map<String id, String title> ---> tentative

        return null;
    }

    public ResponseEntity<?> getToCProject(String projectId) {
        // call repository to get TOC from a given project

        // return String (presumably HTML)

        return null;
    }
}
