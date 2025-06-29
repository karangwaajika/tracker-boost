package com.lab.trackerboost.service.impl;

import com.lab.trackerboost.config.TaskMetrics;
import com.lab.trackerboost.dto.project.ProjectRequestDto;
import com.lab.trackerboost.dto.project.ProjectResponseDto;
import com.lab.trackerboost.exception.ProjectExistsException;
import com.lab.trackerboost.exception.ProjectNotFoundException;
import com.lab.trackerboost.model.ProjectEntity;
import com.lab.trackerboost.repository.ProjectRepository;
import com.lab.trackerboost.service.AuditLogService;
import com.lab.trackerboost.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    ProjectRepository projectRepository;
    ModelMapper modelMapper;
    TaskMetrics taskMetrics;
    AuditLogService auditLogService;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ModelMapper modelMapper, TaskMetrics taskMetrics,
                              AuditLogService auditLogService){

        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.taskMetrics = taskMetrics;
        this.auditLogService = auditLogService;
    }

    @Override
    public ProjectResponseDto create(ProjectRequestDto dto) {
        this.taskMetrics.incrementTasksProcessed(); // increment task processed

        if(findProjectByName(dto.getName()).isPresent()){
            throw new ProjectExistsException(
                    String.format("A project with the name '%s' already exist",
                            dto.getName()));
        }

        ProjectEntity project = this.modelMapper
                                    .map(dto, ProjectEntity.class);
        ProjectEntity savedProject = this.projectRepository.save(project);

        // insert log action for create project
        this.auditLogService.logAction(
                "CREATE", "Project", savedProject.getId().toString(), "user",
                Map.of("name", savedProject.getName(), "description", savedProject.getDescription())
        );

        return this.modelMapper
                .map(savedProject, ProjectResponseDto.class);
    }

    @Override
    public Optional<ProjectEntity> findProjectByName(String name) {
        return this.projectRepository.findProjectByName(name);
    }

    @Override
    @Cacheable("authorsPageable")
    public Page<ProjectResponseDto> findAll(Pageable pageable) {
        this.taskMetrics.incrementTasksProcessed(); // increment task processed

        Page<ProjectEntity> projectEntityList = this.projectRepository.findAll(pageable);
        return projectEntityList
                .map(project->this.modelMapper
                .map(project, ProjectResponseDto.class));
    }

    @Override
    @Caching(
            evict = @CacheEvict(value = "authorsPageable", allEntries = true), // to update the entire list
            put = @CachePut(value = "author", key = "#result.id") // Different cache name for individual authors
    )
    public ProjectResponseDto partialUpdate(ProjectRequestDto projectDto, Long id) {
        this.taskMetrics.incrementTasksProcessed(); // increment task processed

        ProjectEntity project = findProjectById(id)
                .orElseThrow( () -> new ProjectNotFoundException(
                        String.format("A project with the Id '%d' doesn't exist", id))
                );
        // update only fields that are provided
        if(projectDto.getName() != null){
            project.setName(projectDto.getName());
        }
        if(projectDto.getDeadline() != null){
            project.setDeadline(projectDto.getDeadline());
        }
        if(projectDto.getDescription() != null){
            project.setDescription(projectDto.getDescription());
        }
        if(projectDto.getStatus() != null){
            project.setStatus(projectDto.getStatus());
        }

        // insert log action for update project
        this.auditLogService.logAction(
                "UPDATE", "Project", project.getId().toString(), "user",
                Map.of("name", project.getName(), "description", project.getDescription())
        );


        return this.modelMapper
                .map(this.projectRepository.save(project), ProjectResponseDto.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = "authorsPageable", key = "#id")
    public void deleteById(Long id) {
        this.taskMetrics.incrementTasksProcessed(); // increment task processed

        ProjectEntity project = findProjectById(id)
                .orElseThrow( () -> new ProjectNotFoundException(
                        String.format("A project with the Id '%d' doesn't exist", id))
                );

        // insert log action for delete project
        this.auditLogService.logAction(
                "DELETE", "Project", project.getId().toString(), "user",
                Map.of("name", project.getName(), "description", project.getDescription())
        );

        this.projectRepository.deleteById(id);
    }

    @Override
    @Cacheable("authors")
    public List<ProjectResponseDto> findAllProject() {
        this.taskMetrics.incrementTasksProcessed(); // increment task processed

        List<ProjectEntity> projectEntityList = this.projectRepository.findAll();
        return projectEntityList.stream()
                .map(project->this.modelMapper.map(project,
                        ProjectResponseDto.class))
                .toList();
    }

    @Override
    @Cacheable("authorsWithoutTasks")
    public List<ProjectResponseDto> findProjectsWithoutTasks() {
        this.taskMetrics.incrementTasksProcessed(); // increment task processed

        List<ProjectEntity> projectEntityList = this.projectRepository.findProjectsWithoutTasks();
        return projectEntityList
                .stream()
                .map(project->this.modelMapper
                        .map(project, ProjectResponseDto.class))
                .toList();
    }

    @Override
    public Optional<ProjectEntity> findProjectById(Long id) {
        return this.projectRepository.findById(id);
    }

}
