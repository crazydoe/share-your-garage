package com.michal.garageshare.user.service;

import com.michal.garageshare.common.generic.service.AbstractService;
import com.michal.garageshare.user.domain.UserEntity;
import com.michal.garageshare.user.dto.UserDTO;
import org.springframework.stereotype.Service;

public interface UserService extends AbstractService<UserEntity, UserDTO, Long> {

}
