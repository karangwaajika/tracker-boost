package com.lab.trackerboost.service.impl;

import com.lab.trackerboost.dto.authentication.UserRegisterDto;
import com.lab.trackerboost.dto.developer.DeveloperDto;
import com.lab.trackerboost.dto.developer.DeveloperResponseDto;
import com.lab.trackerboost.exception.UserExistsException;
import com.lab.trackerboost.exception.UserNotFoundException;
import com.lab.trackerboost.mapper.manual.DeveloperMapper;
import com.lab.trackerboost.model.DeveloperEntity;
import com.lab.trackerboost.model.SkillEntity;
import com.lab.trackerboost.model.UserEntity;
import com.lab.trackerboost.repository.DeveloperRepository;
import com.lab.trackerboost.service.DeveloperService;
import com.lab.trackerboost.service.SkillService;
import com.lab.trackerboost.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DeveloperServiceImpl implements DeveloperService {
    SkillService skillService;
    DeveloperRepository developerRepository;
    PasswordEncoder passwordEncoder;
    UserService userService;

    public DeveloperServiceImpl(SkillService skillService,
                                DeveloperRepository developerRepository,
                                PasswordEncoder passwordEncoder,
                                UserService userService) {
        this.skillService = skillService;
        this.developerRepository = developerRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public DeveloperResponseDto create(DeveloperDto developerDto) {
        //create user
        UserRegisterDto userDto = developerDto.getUser();
        if (userDto == null) {
            throw new UserNotFoundException("Cannot create a developer without a user object");
        }
        if (this.userService.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserExistsException(
                    String.format("A user with the email '%s' already exists", userDto.getEmail()));
        }
        UserEntity userEntity = UserEntity.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .build();

        Set<SkillEntity> skills = new HashSet<>(skillService.findAllById(developerDto.getSkillIds()));
        DeveloperEntity developer = DeveloperMapper.toEntity(developerDto, skills);

        developer.setUser(userEntity);          // dev owns FK
        userEntity.setDeveloper(developer);     // inverse side (cascade target)

        DeveloperEntity saved = developerRepository.save(developer);

        return DeveloperMapper.toResponseDto(saved);
    }

    @Override
    public Optional<DeveloperEntity> findDeveloperById(Long id) {
        return this.developerRepository.findById(id);
    }


    @Override
    public List<DeveloperResponseDto> findAllDevelopers() {
        List<DeveloperEntity> developers = this.developerRepository.findAll();
        return developers.stream().map(DeveloperMapper::toResponseDto).toList();
    }
}
