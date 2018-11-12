package com.michal.garageshare.user.dto;

import com.michal.garageshare.common.generic.dto.DTOInterface;
import com.michal.garageshare.config.Constants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO representing a user, with his authority.
 */
@Data
@NoArgsConstructor
public class UserDTO implements DTOInterface {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 6)
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;
}
