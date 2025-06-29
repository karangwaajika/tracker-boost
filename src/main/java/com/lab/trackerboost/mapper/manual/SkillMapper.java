package com.lab.trackerboost.mapper.manual;


import com.lab.trackerboost.dto.skill.SkillDto;
import com.lab.trackerboost.dto.skill.SkillResponseDto;
import com.lab.trackerboost.model.SkillEntity;

public class SkillMapper {
    public static SkillResponseDto toResponseDto(SkillEntity skillEntity) {
        SkillResponseDto dto = new SkillResponseDto();
        dto.setId(skillEntity.getId());
        dto.setName(skillEntity.getName());

        return dto;
    }

    public static SkillEntity toEntity(SkillDto dto) {
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setName(dto.getName());

        return skillEntity;
    }
}
