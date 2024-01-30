package com.cooksys.social_media_api.dtos;

import java.util.List;

import com.cooksys.social_media_api.entities.Tweet;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContextDto {
	
	private Tweet target;
	
	private List <Tweet> before;
	
	private List <Tweet> after;
}
