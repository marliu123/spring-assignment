package com.cooksys.social_media_api.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.social_media_api.dtos.CredentialsDto;
import com.cooksys.social_media_api.embeddables.Credentials;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CredentialsMapper {

	CredentialsDto entityToDto(Credentials entity);
			
	List<CredentialsDto> entitiesToDtos(List<Credentials> entities);

}
