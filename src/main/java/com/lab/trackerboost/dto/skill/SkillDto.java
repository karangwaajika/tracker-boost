package com.lab.trackerboost.dto.skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* this is dto is used for inserting or updating */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillDto {
    private String name;
}
