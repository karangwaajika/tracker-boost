package com.lab.trackerboost.service;

import com.lab.trackerboost.dto.task.TaskDto;
import com.lab.trackerboost.dto.task.TaskResponseDto;
import com.lab.trackerboost.model.TaskEntity;
import com.lab.trackerboost.util.DeveloperTaskCount;
import com.lab.trackerboost.util.TaskStatusCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskEntity create(TaskDto taskDto);
    Optional<TaskEntity> findTaskById(Long id);
    Optional<TaskEntity> findTaskByTitle(String title);
    Page<TaskResponseDto> findAll(Pageable pageable);
    TaskEntity partialUpdate(TaskDto taskDto, Long taskId, Authentication auth) throws AccessDeniedException;
    void deleteById(Long id);
    void assignTaskToDeveloper(Long taskId, Long developerId);
    List<TaskResponseDto> findAllByOrderByDueDateAsc();
    List<TaskResponseDto> findOverdueTasks();
    List<DeveloperTaskCount> findTopDevelopers(Pageable pageable);
    List<TaskStatusCount> countTasksByStatus();
}
