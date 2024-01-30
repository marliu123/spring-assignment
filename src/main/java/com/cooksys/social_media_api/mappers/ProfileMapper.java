package com.cooksys.social_media_api.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.social_media_api.dtos.ProfileDto;
import com.cooksys.social_media_api.embeddables.Profile;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ProfileMapper {

	ProfileDto entityToDto(Profile entity);
			
	List<ProfileDto> entitiesToDtos(List<Profile> entities);

	Profile dtoToEntity(ProfileDto profileDto);


}
