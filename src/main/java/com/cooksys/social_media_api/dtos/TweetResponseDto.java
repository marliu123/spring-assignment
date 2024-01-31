package com.cooksys.social_media_api.dtos;

import java.sql.Timestamp;

import com.cooksys.social_media_api.entities.Tweet;
import com.cooksys.social_media_api.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetResponseDto {

	private int id;
	
	private UserResponseDto author;
	
	private Timestamp posted;
	
	private String content;
	
	private TweetResponseDto inReplyTo;
	
	private TweetResponseDto repostOf;
}
