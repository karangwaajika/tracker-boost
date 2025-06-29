package com.lab.trackerboost.mapper.manual;

import com.lab.trackerboost.dto.authentication.DeveloperUserResponseDto;
import com.lab.trackerboost.dto.developer.DeveloperDto;
import com.lab.trackerboost.dto.developer.DeveloperResponseDto;
import com.lab.trackerboost.dto.developer.UserDeveloperResponseDto;
import com.lab.trackerboost.dto.skill.SkillResponseDto;
import com.lab.trackerboost.model.DeveloperEntity;
import com.lab.trackerboost.model.SkillEntity;
import com.lab.trackerboost.model.UserEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperMapper {

    public static DeveloperResponseDto toResponseDto(DeveloperEntity developerEntity) {
        DeveloperResponseDto dto = new DeveloperResponseDto();
        dto.setId(developerEntity.getId());
        dto.setName(developerEntity.getName());

        UserEntity userEntity = developerEntity.getUser();
        DeveloperUserResponseDto userDto = DeveloperUserResponseDto.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();

        dto.setUser(userDto);

        Set<SkillResponseDto> skillDTOs = developerEntity.getSkills().stream()
                .map(skill -> {
                    SkillResponseDto s = new SkillResponseDto();
                    s.setId(skill.getId());
                    s.setName(skill.getName());
                    return s;
                }).collect(Collectors.toSet());

        dto.setSkills(skillDTOs);
        return dto;
    }

    public static UserDeveloperResponseDto toUserDevResponseDto(DeveloperEntity developerEntity) {
        UserDeveloperResponseDto dto = new UserDeveloperResponseDto();
        dto.setId(developerEntity.getId());
        dto.setName(developerEntity.getName());

        Set<SkillResponseDto> skillDTOs = developerEntity.getSkills().stream()
                .map(skill -> {
                    SkillResponseDto s = new SkillResponseDto();
                    s.setId(skill.getId());
                    s.setName(skill.getName());
                    return s;
                }).collect(Collectors.toSet());

        dto.setSkills(skillDTOs);
        return dto;
    }

    // Map DTO → Developer entity (requires skills to be passed in!)
    /* this is better, since the skill need service layer. we perform the service layer
    /at the controller level and pass in the needed skills. */
    public static DeveloperEntity toEntity(DeveloperDto developerDto, Set<SkillEntity> skillsFromDb ) {
        DeveloperEntity dev = new DeveloperEntity();
        dev.setName(developerDto.getName());
        dev.setSkills(new HashSet<>(skillsFromDb));
        return dev;
    }
}
