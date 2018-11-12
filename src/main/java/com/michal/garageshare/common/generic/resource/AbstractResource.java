package com.michal.garageshare.common.generic.resource;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.codahale.metrics.annotation.Timed;
import com.michal.garageshare.common.exception.ObjectNotFoundException;
import com.michal.garageshare.common.generic.domain.EntityInterface;
import com.michal.garageshare.common.generic.dto.DTOInterface;

import com.michal.garageshare.common.generic.service.AbstractService;
import com.michal.garageshare.common.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractResource<ENTITY extends EntityInterface, DTO extends DTOInterface, ID extends Serializable> {
	protected final AbstractService<ENTITY, DTO, ID> service;

	protected abstract String getEntityName();

	@Timed
	@PreAuthorize("hasAuthority('USER')")
	@PostMapping
	public ResponseEntity<DTO> create(@Valid @RequestBody DTO dto, HttpServletRequest request) throws URISyntaxException {
		log.debug("REST request to create {} with given DTO: \n{}", getEntityName(), dto);
		DTO result = service.create(dto);
		return ResponseEntity.created(new URI(request.getRequestURI())).body(result);
	}

	@Timed
	@PreAuthorize("hasAuthority('USER')")
	@PutMapping
	public ResponseEntity<DTO> update(@Valid @RequestBody DTO dto) {
		log.debug("REST request to save {} with given DTO: \n{}", getEntityName(), dto);
		DTO result = service.update(dto);
		return ResponseEntity.ok().body(result);
	}

	@Timed
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping
	public ResponseEntity<List<DTO>> getAll(@ApiParam Pageable pageable, HttpServletRequest request) {
		log.debug("REST request to get all of {} with params: \npageable: {}, \nfilter: {}", getEntityName(), pageable);
		Page<DTO> page = service.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, request.getRequestURI());
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@Timed
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/{id}")
	public ResponseEntity<DTO> getOne(@PathVariable ID id) {
		log.debug("REST request to get one object of {} with id: {}", getEntityName(), id);
		Optional<DTO> one = service.findOne(id);
		return one.map(dto -> ResponseEntity.ok().body(dto)).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Timed
	@PreAuthorize("hasAuthority('USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<DTO> delete(@PathVariable ID id) throws ObjectNotFoundException {
		log.debug("REST request to delete {} with id: {} ", getEntityName(), id);
		DTO dto = service.delete(id);
		return ResponseEntity.ok().body(dto);
	}

}
