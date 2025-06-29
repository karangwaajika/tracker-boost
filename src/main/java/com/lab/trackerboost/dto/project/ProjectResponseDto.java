package com.lab.trackerboost.dto.project;

import com.lab.trackerboost.dto.task.TaskDtoSummary;
import com.lab.trackerboost.util.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String description;
    private LocalDate deadline;
    private ProjectStatus status;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<TaskDtoSummary> tasks;
}
