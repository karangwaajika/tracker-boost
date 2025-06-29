package com.lab.trackerboost.dto.task;

import com.lab.trackerboost.util.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/* this is dto is used for inserting or updating */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
    private Long projectId;
}
