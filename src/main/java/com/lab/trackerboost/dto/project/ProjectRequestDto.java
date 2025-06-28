package com.lab.trackerboost.dto.project;

import com.lab.trackerboost.util.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
/* dto for request*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequestDto {
    private String name;
    private String description;
    private LocalDate deadline;
    private ProjectStatus status;
    private LocalDateTime createdAt = LocalDateTime.now();
}
