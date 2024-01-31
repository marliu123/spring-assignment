package com.cooksys.social_media_api.dtos;

import java.sql.Timestamp;

import com.cooksys.social_media_api.embeddables.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {

	private String username;
	
	private ProfileDto profile;
	
	private Timestamp joined;
}
