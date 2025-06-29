package com.lab.trackerboost.dto.developer;

import com.lab.trackerboost.dto.authentication.DeveloperUserResponseDto;
import com.lab.trackerboost.dto.skill.SkillResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperResponseDto {
    private Long id;
    private String name;
    private Set<SkillResponseDto> skills;
    private DeveloperUserResponseDto user;
}
