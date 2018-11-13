package com.michal.garageshare.common.generic.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.michal.garageshare.common.exception.ObjectNotFoundException;
import com.michal.garageshare.common.generic.domain.EntityInterface;
import com.michal.garageshare.common.generic.dto.DTOInterface;
import com.michal.garageshare.common.generic.mapper.Mapper;
import com.michal.garageshare.common.generic.repository.AbstractRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public abstract class AbstractServiceImpl< ENTITY extends EntityInterface, DTO extends DTOInterface, ID extends Serializable> implements AbstractService<ENTITY, DTO, ID> {
    protected final Mapper<DTO, ENTITY> mapper;
    protected final AbstractRepository<ENTITY, ID> repository;

    @Override
    @Transactional
    public List<DTO> create(List<DTO> dtos) {
        List<ENTITY> entities = mapper.toEntities(dtos);
        List<ENTITY> saved = repository.saveAll(entities);
        return mapper.toDtos(saved);
    }

    @Override
    @Transactional
    public DTO create(DTO dto) {
        ENTITY entity = mapper.toEntity(dto);
        ENTITY saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public DTO update(DTO dto) {
        ENTITY entity = mapper.toEntity(dto);
        ENTITY saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public DTO delete(ID id) throws ObjectNotFoundException {
        ENTITY entity = repository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException());
        repository.delete(entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DTO> findAll() {
        List<ENTITY> all = repository.findAll();
        if(CollectionUtils.isNotEmpty(all)) {
            return mapper.toDtos(all);
        }
        return Lists.newArrayList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DTO> findOne(ID id) {
        Optional<ENTITY> one = repository.findById(id);
        return one.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DTO> findByIds(Collection<ID> ids) {
        List<ENTITY> allById = repository.findAllById(ids);
        return mapper.toDtos(allById);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(ID id) {
        return repository.existsById(id);
    }

}
