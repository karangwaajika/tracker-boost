package com.lab.trackerboost.dto.developer;

import com.lab.trackerboost.dto.skill.SkillResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

// this dto is used when developer is a sub-object of a user class
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDeveloperResponseDto {
    private Long id;
    private String name;
    private Set<SkillResponseDto> skills;
}
