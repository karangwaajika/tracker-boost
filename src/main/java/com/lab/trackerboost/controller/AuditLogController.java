package com.lab.trackerboost.controller;

import com.lab.trackerboost.model.AuditLogEntity;
import com.lab.trackerboost.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/logs")
@Tag(name = "AuditLog Controller", description = "Manage all the Developer's urls")
public class AuditLogController {
    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/view")
    @Operation(summary = "View AuditLog")
    public ResponseEntity<List<AuditLogEntity>> getLogs(
            @RequestParam Optional<String> entityType,
            @RequestParam Optional<String> actorName
    ) {
        List<AuditLogEntity> logs = auditLogService.getLogs(entityType, actorName);
        return ResponseEntity.ok(logs);
    }
}

