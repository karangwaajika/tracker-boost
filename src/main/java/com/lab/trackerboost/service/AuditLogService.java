package com.lab.trackerboost.service;


import com.lab.trackerboost.model.AuditLogEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AuditLogService {
    void logAction(String actionType, String entityType, String entityId, String actor, Map<String, Object> payload);
    List<AuditLogEntity> getLogs(Optional<String> entityType, Optional<String> actorName);
}
