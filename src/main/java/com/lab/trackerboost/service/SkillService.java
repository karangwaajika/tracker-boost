package com.lab.trackerboost.service;

import com.lab.trackerboost.dto.skill.SkillDto;
import com.lab.trackerboost.dto.skill.SkillResponseDto;
import com.lab.trackerboost.model.SkillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface SkillService {
    SkillEntity create(SkillDto skillDto);
    Optional<SkillEntity> findSkillByName(String name);
    Optional<SkillEntity> findSkillById(Long id);
    Set<SkillEntity> findAllById(Set<Long> ids);
    Page<SkillResponseDto> findAll(Pageable pageable);


}
