package com.cooksys.social_media_api.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.social_media_api.dtos.UserRequestDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;
import com.cooksys.social_media_api.entities.User;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})

public interface UserMapper {
    @Mapping(target = "username", source = "credentials.username")
	UserResponseDto entityToDto(User entity);
			
	List<UserResponseDto> entitiesToDtos(List<User> entities);

	User requestDtoToEntity(UserRequestDto entity);

	List<User> requestDtosToEntities(List<UserRequestDto> entities);
}
