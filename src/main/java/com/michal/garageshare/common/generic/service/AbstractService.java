package com.michal.garageshare.common.generic.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.michal.garageshare.common.exception.ObjectNotFoundException;
import com.michal.garageshare.common.generic.domain.EntityInterface;
import com.michal.garageshare.common.generic.dto.DTOInterface;

public interface AbstractService<ENTITY extends EntityInterface, DTO extends DTOInterface, ID extends Serializable> {

    DTO create(DTO dto);

    List<DTO> create(List<DTO> dtos);

    DTO update(DTO dto);

    DTO delete(ID id) throws ObjectNotFoundException;

    List<DTO> findAll();

    Page<DTO> findAll(Pageable pageable);

    Optional<DTO> findOne(ID id);

    List<DTO> findByIds(Collection<ID> ids);

    boolean exists(ID id);
}
