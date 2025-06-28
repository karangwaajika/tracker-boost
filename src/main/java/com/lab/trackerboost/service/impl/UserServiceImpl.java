package com.lab.trackerboost.service.impl;

import com.lab.trackerboost.dto.authentication.UserRegisterDto;
import com.lab.trackerboost.dto.authentication.UserResponseDto;
import com.lab.trackerboost.model.UserEntity;
import com.lab.trackerboost.repository.UserRepository;
import com.lab.trackerboost.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity create(UserRegisterDto userDto) {
        // hash the password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity user = UserEntity.builder()
                            .email(userDto.getEmail())
                            .password(userDto.getPassword())
                            .role(userDto.getRole())
                            .build();

        return this.userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findByEmail(String username) {
        return this.userRepository.findByEmail(username);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }
}
