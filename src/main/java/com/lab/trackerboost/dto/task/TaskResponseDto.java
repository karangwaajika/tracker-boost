package com.lab.trackerboost.dto.task;

import com.lab.trackerboost.dto.project.ProjectDtoSummary;
import com.lab.trackerboost.util.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private ProjectDtoSummary project;
}
