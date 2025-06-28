package com.lab.trackerboost.service;

import com.lab.trackerboost.dto.authentication.UserRegisterDto;
import com.lab.trackerboost.dto.authentication.UserResponseDto;
import com.lab.trackerboost.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    UserEntity create(UserRegisterDto userDto);
    Optional<UserEntity> findByEmail(String email);
    Page<UserEntity> findAll(Pageable pageable);

}
