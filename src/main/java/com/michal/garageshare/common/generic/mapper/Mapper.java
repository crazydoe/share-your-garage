package com.michal.garageshare.common.generic.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.michal.garageshare.common.generic.domain.EntityInterface;
import com.michal.garageshare.common.generic.dto.DTOInterface;

public interface Mapper<DTO extends DTOInterface, ENTITY extends EntityInterface> {

    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);

    default List<DTO> toDtos(List<ENTITY> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<ENTITY> toEntities(List<DTO> dtos){
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

}
