package com.ixxus.etram.migration.infrastructure.rest;

import com.ixxus.etram.migration.application.services.MigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/migration")
@RequiredArgsConstructor
public class MigrationController {

    private final MigrationService migrationService;

    @GetMapping("/{projectId}/{space}/migrate")
    public ResponseEntity<?> migrateProject(@PathVariable Integer projectId, @PathVariable String space) {

        return ResponseEntity.status(HttpStatus.OK).body(migrationService.migrate(projectId, space));
    }

}
