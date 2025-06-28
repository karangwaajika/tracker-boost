package com.lab.trackerboost.repository;

import com.lab.trackerboost.model.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Optional<ProjectEntity> findProjectByName(String name);
    @Query("SELECT p FROM ProjectEntity p LEFT JOIN TaskEntity t ON p = t.project WHERE t.id IS NULL")
    List<ProjectEntity> findProjectsWithoutTasks();
}
