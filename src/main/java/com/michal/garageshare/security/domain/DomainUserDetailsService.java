package com.michal.garageshare.security.domain;

import com.michal.garageshare.security.authority.Authority;
import com.michal.garageshare.security.exception.UserNotActivatedException;
import com.michal.garageshare.user.domain.UserEntity;
import com.michal.garageshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.michal.garageshare.security.authority.AuthoritiesConstants.USER;

@Slf4j
@RequiredArgsConstructor
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);

		if (new EmailValidator().isValid(login, null)) {
			return userRepository.findOneByEmail(login).map(user -> createSpringSecurityUser(login, user))
								 .orElseThrow(() -> new UsernameNotFoundException("UserEntity with email " + login + " was not found in the database"));
		}

		String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
		return userRepository.findOneByLogin(lowercaseLogin).map(user -> createSpringSecurityUser(lowercaseLogin, user))
							 .orElseThrow(() -> new UsernameNotFoundException("UserEntity " + lowercaseLogin + " was not found in the database"));

	}

	private User createSpringSecurityUser(String lowercaseLogin, UserEntity user) {
		if (!user.isActivated()) {
			throw new UserNotActivatedException("UserEntity " + lowercaseLogin + " was not activated");
		}
		Authority authority = user.getAuthority();
		List<GrantedAuthority> grantedAuthorities = Stream.of(authority, USER).map(a -> new SimpleGrantedAuthority(a.toString())).collect(Collectors.toList());
		return new User(user.getLogin(), user.getPassword(), grantedAuthorities);
	}
}
