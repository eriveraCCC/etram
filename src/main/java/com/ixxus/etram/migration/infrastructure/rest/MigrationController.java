package com.ixxus.etram.migration.infrastructure.rest;

import com.ixxus.etram.migration.application.services.MigrationService;
import com.ixxus.etram.migration.model.entity.CreatedArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/migration")
@RequiredArgsConstructor
public class MigrationController {

    private final MigrationService migrationService;

    @GetMapping("/{projectId}/{space}/migrate")
    public ResponseEntity<List<CreatedArticle>> migrateProject(@PathVariable Integer projectId, @PathVariable String space) {

        return ResponseEntity.status(HttpStatus.OK).body(migrationService.migrate(projectId, space));
    }

    @PutMapping("/{projectId}/{space}/links")
    public ResponseEntity<String> fixBrokenLinks(@PathVariable Integer projectId, @PathVariable String space) {

        migrationService.fixBrokenLinks(projectId, space);

        return ResponseEntity.status(HttpStatus.OK).body("bacano");
    }

}
