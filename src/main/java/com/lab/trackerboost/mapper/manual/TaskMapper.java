package com.lab.trackerboost.mapper.manual;

import com.lab.trackerboost.dto.project.ProjectDtoSummary;
import com.lab.trackerboost.dto.task.TaskDto;
import com.lab.trackerboost.dto.task.TaskResponseDto;
import com.lab.trackerboost.model.ProjectEntity;
import com.lab.trackerboost.model.TaskEntity;

public class TaskMapper {
    public static TaskEntity toEntity(TaskDto taskDto, ProjectEntity project) {
        return TaskEntity.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus())
                .dueDate(taskDto.getDueDate())
                .project(project)
                .build();
    }

    public static TaskResponseDto toResponseDto(TaskEntity taskEntity) {
        ProjectEntity project = taskEntity.getProject();

        ProjectDtoSummary projectDto = new ProjectDtoSummary();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setDeadline(project.getDeadline());
        projectDto.setStatus(project.getStatus());
        projectDto.setDescription(project.getDescription());
        projectDto.setCreatedAt(project.getCreatedAt());

        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(taskEntity.getId());
        dto.setTitle(taskEntity.getTitle());
        dto.setDescription(taskEntity.getDescription());
        dto.setStatus(taskEntity.getStatus());
        dto.setDueDate(taskEntity.getDueDate());
        /* with this projectDto we're no longer returning the actual Project
        * in which the task list is present. here we created the project without */
        dto.setProject(projectDto);

        return dto;
    }
}
