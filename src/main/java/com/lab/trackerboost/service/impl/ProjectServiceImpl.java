package com.lab.trackerboost.service.impl;

import com.lab.trackerboost.dto.project.ProjectRequestDto;
import com.lab.trackerboost.model.ProjectEntity;
import com.lab.trackerboost.repository.ProjectRepository;
import com.lab.trackerboost.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository){

        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectEntity create(ProjectRequestDto dto) {
        ProjectEntity project = new ProjectEntity();

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setDeadline(dto.getDeadline());
        project.setStatus(dto.getStatus());
        project.setCreatedAt(LocalDateTime.now());
        project.setTaskEntities(new ArrayList<>()); // not set

        return this.projectRepository.save(project);
    }

    @Override
    public Optional<ProjectEntity> findProjectByName(String name) {
        return this.projectRepository.findProjectByName(name);
    }

    @Override
    public Page<ProjectEntity> findAll(Pageable pageable) {
        return this.projectRepository.findAll(pageable);
    }

    @Override
    public ProjectEntity partialUpdate(ProjectEntity projectDto, Long id) {
        ProjectEntity project = findProjectById(id)
                .orElseThrow();
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

        return this.projectRepository.save(project);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ProjectEntity project = findProjectById(id).orElseThrow();

        this.projectRepository.deleteById(id);
    }

    @Override
    public List<ProjectEntity> findAllProject() {
        return this.projectRepository.findAll();
    }

    @Override
    public List<ProjectEntity> findProjectsWithoutTasks() {
        return this.projectRepository.findProjectsWithoutTasks();
    }

    @Override
    public Optional<ProjectEntity> findProjectById(Long id) {
        return this.projectRepository.findById(id);
    }

}
