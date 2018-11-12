package com.michal.garageshare.authentication.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.michal.garageshare.user.dto.ManagedUserDTO;

import lombok.Data;

@Data
public class AuthenticationDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = ManagedUserDTO.PASSWORD_MIN_LENGTH, max = ManagedUserDTO.PASSWORD_MAX_LENGTH)
    private String password;

    private boolean rememberMe;

}
