package com.michal.garageshare.audit.repository;

import com.michal.garageshare.audit.domain.PersistentAuditEventEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the PersistentAuditEventEntity entity.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEventEntity, Long> {

    List<PersistentAuditEventEntity> findByPrincipal(String principal);

    List<PersistentAuditEventEntity> findByAuditEventDateAfter(Instant after);

    List<PersistentAuditEventEntity> findByPrincipalAndAuditEventDateAfter(String principal, Instant after);

    List<PersistentAuditEventEntity> findByPrincipalAndAuditEventDateAfterAndAuditEventType(String principal, Instant after, String type);

    Page<PersistentAuditEventEntity> findAllByAuditEventDateBetween(Instant fromDate, Instant toDate, Pageable pageable);
}
