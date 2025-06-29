package com.lab.trackerboost.service.impl;

import com.lab.trackerboost.dto.skill.SkillDto;
import com.lab.trackerboost.dto.skill.SkillResponseDto;
import com.lab.trackerboost.mapper.manual.SkillMapper;
import com.lab.trackerboost.model.SkillEntity;
import com.lab.trackerboost.repository.SkillRepository;
import com.lab.trackerboost.service.SkillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {
    SkillRepository skillRepository;
    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public SkillEntity create(SkillDto skillDto) {
        if(findSkillByName(skillDto.getName()).isPresent()){
            throw new SkillExistsException(
                    String.format("A skill with the name '%s' already exist",
                            skillDto.getName()));
        }
        SkillEntity skillEntity = SkillMapper.toEntity(skillDto);
        return this.skillRepository.save(skillEntity);
    }

    @Override
    public Optional<SkillEntity> findSkillByName(String name) {
        return this.skillRepository.findSkillByName(name);
    }

    @Override
    public Optional<SkillEntity> findSkillById(Long id) {
        return this.skillRepository.findById(id);
    }

    @Override
    public Set<SkillEntity> findAllById(Set<Long> ids) {
        List<SkillEntity> skills = skillRepository.findAllById(ids);

        // Validation: ensure all requested IDs are found
        Set<Long> foundIds = skills.stream()
                .map(SkillEntity::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = new HashSet<>(ids);
        missingIds.removeAll(foundIds);

        if (!missingIds.isEmpty()) {
            throw new InvalidSkillException("Invalid skill IDs: " + missingIds);
        }
        new HashSet<>(skillRepository.findAllById(ids));
        return new HashSet<>(skillRepository.findAllById(ids));
    }

    @Override
    public Page<SkillResponseDto> findAll(Pageable pageable) {
        Page<SkillEntity> taskEntityPage = this.skillRepository.findAll(pageable);
        return taskEntityPage.map(SkillMapper::toResponseDto);
    }
}
