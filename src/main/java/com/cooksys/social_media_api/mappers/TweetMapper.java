package com.cooksys.social_media_api.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.social_media_api.dtos.TweetRequestDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.entities.Tweet;



@Mapper(componentModel = "spring")
public interface TweetMapper {

	TweetResponseDto entityToDto(Tweet entity);
			
	List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);
	
	Tweet requestDtoToEntity(TweetRequestDto entity);
	
	List<Tweet> requestDtosToEntity(List<TweetRequestDto> entities);


}
