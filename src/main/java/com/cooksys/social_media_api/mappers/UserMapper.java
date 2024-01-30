package com.cooksys.social_media_api.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.social_media_api.dtos.UserRequestDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;
import com.cooksys.social_media_api.entities.User;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

	UserResponseDto entityToDto(User entity);
			
	List<UserResponseDto> entitiesToDtos(List<User> entities);

	User requestDtoToEntity(UserRequestDto entity);
	
	List<User> requestDtosToEntities(List<UserRequestDto> entities);
}
