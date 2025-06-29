package com.lab.trackerboost.mapper.manual;

import com.lab.trackerboost.dto.authentication.UserRegisterDto;
import com.lab.trackerboost.dto.authentication.UserResponseDto;
import com.lab.trackerboost.model.DeveloperEntity;
import com.lab.trackerboost.model.UserEntity;

public class UserMapper {

    public static UserResponseDto toDto(UserEntity userEntity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(userEntity.getId());
        dto.setEmail(userEntity.getEmail());
        dto.setRole(userEntity.getRole());

        return dto;
    }

    public static UserEntity toEntity(UserRegisterDto userDto, DeveloperEntity developer) {

        return UserEntity.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .developer(developer)
                .build();
    }
}
