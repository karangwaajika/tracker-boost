package com.lab.trackerboost.service.impl;

import com.lab.trackerboost.dto.project.ProjectRequestDto;
import com.lab.trackerboost.dto.project.ProjectResponseDto;
import com.lab.trackerboost.model.ProjectEntity;
import com.lab.trackerboost.repository.ProjectRepository;
import com.lab.trackerboost.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    ProjectRepository projectRepository;
    ModelMapper modelMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ModelMapper modelMapper){

        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProjectResponseDto create(ProjectRequestDto dto) {
        ProjectEntity project = this.modelMapper
                                    .map(projectRepository, ProjectEntity.class);
        return this.modelMapper
                .map(this.projectRepository.save(project), ProjectResponseDto.class);
    }

    @Override
    public Optional<ProjectEntity> findProjectByName(String name) {
        return this.projectRepository.findProjectByName(name);
    }

    @Override
    public Page<ProjectResponseDto> findAll(Pageable pageable) {
        Page<ProjectEntity> projectEntityList = this.projectRepository.findAll(pageable);
        return projectEntityList
                .map(project->this.modelMapper
                .map(project, ProjectResponseDto.class));
    }

    @Override
    public ProjectResponseDto partialUpdate(ProjectRequestDto projectDto, Long id) {
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

        return this.modelMapper
                .map(this.projectRepository.save(project), ProjectResponseDto.class);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ProjectEntity project = findProjectById(id).orElseThrow();

        this.projectRepository.deleteById(id);
    }

    @Override
    public List<ProjectResponseDto> findAllProject() {
        List<ProjectEntity> projectEntityList = this.projectRepository.findAll();
        return projectEntityList.stream()
                .map(project->this.modelMapper.map(project,
                        ProjectResponseDto.class))
                .toList();
    }

    @Override
    public List<ProjectResponseDto> findProjectsWithoutTasks() {
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
