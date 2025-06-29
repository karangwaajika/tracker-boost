package com.lab.trackerboost.service.impl;

import com.lab.trackerboost.dto.task.TaskDto;
import com.lab.trackerboost.dto.task.TaskResponseDto;
import com.lab.trackerboost.exception.DeveloperNotFoundException;
import com.lab.trackerboost.exception.ProjectNotFoundException;
import com.lab.trackerboost.exception.TaskExistsException;
import com.lab.trackerboost.exception.TaskNotFoundException;
import com.lab.trackerboost.mapper.manual.TaskMapper;
import com.lab.trackerboost.model.DeveloperEntity;
import com.lab.trackerboost.model.ProjectEntity;
import com.lab.trackerboost.model.TaskEntity;
import com.lab.trackerboost.repository.TaskRepository;
import com.lab.trackerboost.service.DeveloperService;
import com.lab.trackerboost.service.ProjectService;
import com.lab.trackerboost.service.TaskService;
import com.lab.trackerboost.util.DeveloperTaskCount;
import com.lab.trackerboost.util.TaskStatusCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    TaskRepository taskRepository;
    ProjectService projectService;
    DeveloperService developerService;

    public TaskServiceImpl(TaskRepository taskRepository,
                           ProjectService projectService,
                           DeveloperService developerService) {

        this.taskRepository = taskRepository;
        this.projectService = projectService;
        this.developerService = developerService;
    }

    @Override
    public TaskEntity create(TaskDto taskDto) {
        Optional<ProjectEntity> project = this.projectService.findProjectById(taskDto.getProjectId());
        if(project.isEmpty()){
            throw new ProjectNotFoundException(
                    String.format("A project with the Id '%d' doesn't exist",
                            taskDto.getProjectId()));
        }

        if(findTaskByTitle(taskDto.getTitle()).isPresent()){
            throw new TaskExistsException(
                    String.format("A task with the title '%s' already exist",
                            taskDto.getTitle()));
        }

        TaskEntity taskEntity = TaskMapper.toEntity(taskDto, project.get());
        return this.taskRepository.save(taskEntity);
    }

    @Override
    public Optional<TaskEntity> findTaskById(Long id) {
        return this.taskRepository.findById(id);
    }

    @Override
    public Optional<TaskEntity> findTaskByTitle(String title) {
        return this.taskRepository.findTaskEntitiesByTitle(title);
    }

    @Override
    public Page<TaskResponseDto> findAll(Pageable pageable) {
        Page<TaskEntity> taskEntityPage = this.taskRepository.findAll(pageable);

        return taskEntityPage.map(TaskMapper::toResponseDto);
    }

    @Override
    public TaskEntity partialUpdate(TaskDto taskDto, Long taskId, Authentication auth) throws AccessDeniedException {
        TaskEntity taskEntity = findTaskById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(
                        String.format("A task with the Id '%d' doesn't exist", taskId)));

        DeveloperEntity dev = taskEntity.getDeveloper();
        // check whether auth email matches the developer assigned
        String authEmail = auth.getName();
        if(!dev.getUser().getEmail().equals(authEmail)){
            throw new AccessDeniedException("You are not allowed to update this task");
        }
        if(taskDto.getTitle() != null){
            taskEntity.setTitle(taskDto.getTitle());
        }
        if(taskDto.getDescription() != null){
            taskEntity.setDescription(taskDto.getDescription());
        }
        if(taskDto.getDueDate() != null){
            taskEntity.setDueDate(taskDto.getDueDate());
        }
        if(taskDto.getStatus() != null){
            taskEntity.setStatus(taskDto.getStatus());
        }
        if(taskDto.getProjectId() != null){
            Optional<ProjectEntity> project = this.projectService.findProjectById(taskDto.getProjectId());
            if(project.isEmpty()){
                throw new ProjectNotFoundException(
                        String.format("A project with the Id '%d' doesn't exist",
                                taskDto.getProjectId()));
            }
            taskEntity.setProject(project.get());
        }
        return this.taskRepository.save(taskEntity);
    }

    @Override
    public void deleteById(Long id) {
        if(findTaskById(id).isEmpty()){
            throw new TaskNotFoundException(
                    String.format("A task with the Id '%d' doesn't exist", id));
        }
        this.taskRepository.deleteById(id);
    }

    @Override
    public void assignTaskToDeveloper(Long taskId, Long developerId) {
        TaskEntity task = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(
                        String.format("A task with the Id '%d' doesn't exist", taskId)
                ));
        DeveloperEntity developer = this.developerService.findDeveloperById(developerId)
                .orElseThrow(() -> new DeveloperNotFoundException(
                        String.format("A developer with the Id '%d' doesn't exist", developerId)
                ));

        task.setDeveloper(developer);
        this.taskRepository.save(task);
    }

    @Override
    public List<TaskResponseDto> findAllByOrderByDueDateAsc() {
        List<TaskEntity> tasks = taskRepository.findAllByOrderByDueDateAsc();
        return tasks.stream().map(TaskMapper::toResponseDto).toList();
    }

    @Override
    public List<TaskResponseDto> findOverdueTasks() {
        List<TaskEntity> tasks = this.taskRepository.findOverdueTasks();
        return tasks.stream().map(TaskMapper::toResponseDto).toList();
    }

    @Override
    public List<DeveloperTaskCount> findTopDevelopers(Pageable pageable) {
        return this.taskRepository.findTopDevelopers(pageable);
    }

    @Override
    public List<TaskStatusCount> countTasksByStatus() {
        return this.taskRepository.countTasksByStatus();
    }

}
