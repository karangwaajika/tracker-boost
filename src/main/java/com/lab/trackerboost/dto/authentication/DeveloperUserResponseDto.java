package com.lab.trackerboost.dto.authentication;

import com.lab.trackerboost.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//this dto is used when user is sub-object of a developer class
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperUserResponseDto {
    private Long id;
    private String email;
    private Role role;
}
