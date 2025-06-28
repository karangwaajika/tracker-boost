package com.lab.trackerboost.repository;

import com.lab.trackerboost.model.TaskEntity;
import com.lab.trackerboost.util.DeveloperTaskCount;
import com.lab.trackerboost.util.TaskStatusCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Optional<TaskEntity> findTaskEntitiesByTitle(String title);
    List<TaskEntity> findAllByOrderByDueDateAsc();
    @Query("SELECT t FROM TaskEntity t WHERE t.dueDate < CURRENT_DATE")
    List<TaskEntity> findOverdueTasks();
    @Query("SELECT t.developer.name AS developerName, COUNT(t) AS taskCount " +
           "FROM TaskEntity t GROUP BY t.developer.name ORDER BY COUNT(t) DESC")
    List<DeveloperTaskCount> findTopDevelopers(Pageable pageable);
    @Query("SELECT t.status AS status, COUNT(t) AS count FROM TaskEntity t GROUP BY t.status")
    List<TaskStatusCount> countTasksByStatus();
}
