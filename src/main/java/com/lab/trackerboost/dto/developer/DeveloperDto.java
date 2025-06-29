package com.lab.trackerboost.dto.developer;

import com.lab.trackerboost.dto.authentication.UserRegisterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/* this is dto is used for inserting or updating */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperDto {
    private String name;
    private Set<Long> skillIds; // IDs of existing skills
    private UserRegisterDto user;
}
