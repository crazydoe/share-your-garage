package com.michal.garageshare.user.resource;

import com.michal.garageshare.common.generic.resource.AbstractResource;
import com.michal.garageshare.common.generic.service.AbstractService;
import com.michal.garageshare.config.Constants;
import com.michal.garageshare.user.domain.UserEntity;
import com.michal.garageshare.user.repository.UserRepository;
import com.michal.garageshare.security.authority.AuthoritiesConstants;
import com.michal.garageshare.mail.service.MailService;
import com.michal.garageshare.user.service.UserService;
import com.michal.garageshare.user.service.UserServiceImpl;
import com.michal.garageshare.user.dto.UserDTO;
import com.michal.garageshare.common.exception.BadRequestAlertException;
import com.michal.garageshare.common.exception.EmailAlreadyUsedException;
import com.michal.garageshare.common.exception.LoginAlreadyUsedException;
import com.michal.garageshare.common.util.HeaderUtil;
import com.michal.garageshare.common.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserResource extends AbstractResource<UserEntity, UserDTO, Long> {

	public UserResource(AbstractService<UserEntity, UserDTO, Long> service) {
		super(service);
	}

	@Override
	protected String getEntityName() {
		return null;
	}
}
