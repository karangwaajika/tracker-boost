package com.lab.trackerboost.service;

import com.lab.trackerboost.dto.developer.DeveloperDto;
import com.lab.trackerboost.dto.developer.DeveloperResponseDto;
import com.lab.trackerboost.model.DeveloperEntity;

import java.util.List;
import java.util.Optional;

public interface DeveloperService {
    DeveloperResponseDto create(DeveloperDto developerDto);
    Optional<DeveloperEntity> findDeveloperById(Long id);
    List<DeveloperResponseDto> findAllDevelopers();
}
