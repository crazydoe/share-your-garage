package com.michal.garageshare.user.repository;

import com.michal.garageshare.common.generic.repository.AbstractRepository;
import com.michal.garageshare.user.domain.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.Instant;

/**
 * Spring Data JPA repository for the UserEntity entity.
 */
@Repository
public interface UserRepository extends AbstractRepository<UserEntity, Long> {

    Optional<UserEntity> findOneByActivationKey(String activationKey);

    List<UserEntity> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

    Optional<UserEntity> findOneByResetKey(String resetKey);

    Optional<UserEntity> findOneByEmailIgnoreCase(String email);

    Optional<UserEntity> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authority")
    Optional<UserEntity> findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authority")
    Optional<UserEntity> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authority")
    Optional<UserEntity> findOneWithAuthoritiesByEmail(String email);

    Page<UserEntity> findAllByLoginNot(Pageable pageable, String login);
}
