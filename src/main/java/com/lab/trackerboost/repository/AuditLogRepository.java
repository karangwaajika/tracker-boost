package com.lab.trackerboost.repository;

import com.lab.trackerboost.model.AuditLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLogEntity, Long> {
    List<AuditLogEntity> findByEntityType(String entityType);
    List<AuditLogEntity> findByActorName(String actorName);
    List<AuditLogEntity> findByEntityTypeAndActorName(String entityType, String actorName);
}
