package com.michal.garageshare.user.dto;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class ManagedUserDTO extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
}
