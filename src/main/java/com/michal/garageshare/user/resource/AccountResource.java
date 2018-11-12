package com.michal.garageshare.user.resource;

import com.codahale.metrics.annotation.Timed;
import com.michal.garageshare.common.exception.EmailAlreadyUsedException;
import com.michal.garageshare.common.exception.EmailNotFoundException;
import com.michal.garageshare.common.exception.InternalServerErrorException;
import com.michal.garageshare.common.exception.InvalidPasswordException;
import com.michal.garageshare.common.util.SecurityUtils;
import com.michal.garageshare.mail.service.MailService;
import com.michal.garageshare.user.domain.UserEntity;
import com.michal.garageshare.user.dto.KeyAndPasswordDTO;
import com.michal.garageshare.user.dto.ManagedUserDTO;
import com.michal.garageshare.user.dto.PasswordChangeDTO;
import com.michal.garageshare.user.dto.UserDTO;
import com.michal.garageshare.user.repository.UserRepository;
import com.michal.garageshare.user.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final MailService mailService;

    public AccountResource(UserRepository userRepository, UserServiceImpl userService, MailService mailService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserDTO managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        UserEntity user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
    }

    @GetMapping("/activate")
    @Timed
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<UserEntity> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this activation key");
        }
    }

    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

//    @GetMapping("/account")
//    @Timed
//    public UserDTO getAccount() {
////        return userService.getUserWithAuthorities()
////            .map(UserDTO::new)
////            .orElseThrow(() -> new InternalServerErrorException("UserEntity could not be found"));
//    }

    @PostMapping("/account")
    @Timed
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<UserEntity> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<UserEntity> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("UserEntity could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
            userDTO.getLangKey(), userDTO.getImageUrl());
    }

    @PostMapping(path = "/account/change-password")
    @Timed
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    @PostMapping(path = "/account/reset-password/init")
    @Timed
    public void requestPasswordReset(@RequestBody String mail) {
       mailService.sendPasswordResetMail(
           userService.requestPasswordReset(mail)
               .orElseThrow(EmailNotFoundException::new)
       );
    }

    @PostMapping(path = "/account/reset-password/finish")
    @Timed
    public void finishPasswordReset(@RequestBody KeyAndPasswordDTO keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<UserEntity> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserDTO.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserDTO.PASSWORD_MAX_LENGTH;
    }
}
