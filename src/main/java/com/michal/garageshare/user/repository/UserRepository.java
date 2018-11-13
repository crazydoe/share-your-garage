package com.michal.garageshare.user.repository;

import com.michal.garageshare.common.generic.repository.AbstractRepository;
import com.michal.garageshare.user.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends AbstractRepository<UserEntity, Long> {

	Optional<UserEntity> findOneByActivationKey(String activationKey);

	List<UserEntity> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

	Optional<UserEntity> findOneByResetKey(String resetKey);

	Optional<UserEntity> findOneByEmailIgnoreCase(String email);

	Optional<UserEntity> findOneByLogin(String login);

	Optional<UserEntity> findOneByEmail(String email);

	Page<UserEntity> findAllByLoginNot(Pageable pageable, String login);
}
