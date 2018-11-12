package com.michal.garageshare.user.mapper;

import com.michal.garageshare.common.generic.mapper.Mapper;
import com.michal.garageshare.user.domain.UserEntity;
import com.michal.garageshare.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserDTO, UserEntity> {

	private final ModelMapper mapper;

	@Override
	public UserDTO toDto(UserEntity entity) {
		return mapper.map(entity, UserDTO.class);
	}

	@Override
	public UserEntity toEntity(UserDTO dto) {
		return mapper.map(dto, UserEntity.class);
	}
}
