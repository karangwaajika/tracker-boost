package com.lab.trackerboost.service;

import com.lab.trackerboost.dto.project.ProjectRequestDto;
import com.lab.trackerboost.dto.project.ProjectResponseDto;
import com.lab.trackerboost.model.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    ProjectResponseDto create(ProjectRequestDto project);
    Optional<ProjectEntity> findProjectById(Long id);
    Optional<ProjectEntity> findProjectByName(String name);
    Page<ProjectResponseDto> findAll(Pageable pageable);
    ProjectResponseDto partialUpdate(ProjectRequestDto project, Long id);
    void deleteById(Long id);
    List<ProjectResponseDto> findAllProject();
    List<ProjectResponseDto> findProjectsWithoutTasks();
}
