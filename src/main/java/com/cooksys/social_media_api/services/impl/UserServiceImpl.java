package com.cooksys.social_media_api.services.impl;

import com.cooksys.social_media_api.dtos.CredentialsDto;
import com.cooksys.social_media_api.dtos.ProfileDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.dtos.UserRequestDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;
import com.cooksys.social_media_api.embeddables.Profile;
import com.cooksys.social_media_api.entities.User;
import com.cooksys.social_media_api.exceptions.NotFoundException;
import com.cooksys.social_media_api.mappers.ProfileMapper;
import com.cooksys.social_media_api.mappers.UserMapper;
import com.cooksys.social_media_api.repositories.UserRepository;
import com.cooksys.social_media_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final ProfileMapper profileMapper;
	
    @Override
    public List<UserResponseDto> getAllNonDeletedUsers() {
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
    	User user = userMapper.requestDtoToEntity(userRequestDto);
    	user.setDeleted(false);
    	// still needs editing
    	return userMapper.entityToDto(userRepository.saveAndFlush(user));
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
    	User user = userRepository.findByUsername(username);
    	if(user == null) {
    		throw new NotFoundException("User not found with username: "+username);
    	}
    	
        return userMapper.entityToDto(user);
    }

    @Override
    public UserResponseDto updateUserByUsername(String username, CredentialsDto credentials, ProfileDto profile) {
    	User user = userRepository.findByUsername(username);
    	Profile savedProfile = profileMapper.dtoToEntity(profile);
    	if(user == null || user.getCredentials().getUsername() != credentials.getUsername() || user.getCredentials().getPassword() != credentials.getPassword()) {
    		throw new NotFoundException("User not found");
    	}
    	user.setProfile(savedProfile);
    	return userMapper.entityToDto(userRepository.saveAndFlush(user));
    }

    @Override
    public UserResponseDto deleteUserByUsername(String username, CredentialsDto credentials) {
    	User user = userRepository.findByUsername(username);
    	if(user == null || user.getCredentials().getUsername() != credentials.getUsername() || user.getCredentials().getPassword() != credentials.getPassword()) {
    		throw new NotFoundException("User not found");
    	}
    	user.setDeleted(true);
    	return userMapper.entityToDto(userRepository.saveAndFlush(user)); 
    }

    @Override
    public void followUserByUsername(String username, UserRequestDto user) {

    }

    @Override
    public void unfollowUserByUsername(String username, UserRequestDto user) {

    }

    @Override
    public List<TweetResponseDto> getAllNonDeletedTweetsByUsername(String username) {
        return null;
    }

    @Override
    public List<TweetResponseDto> getAllTweetsUserIsMentionedIn(String username) {
        return null;
    }

    @Override
    public List<UserResponseDto> getAllActiveFollowersOfUser(String username) {
        return null;
    }

    @Override
    public List<UserResponseDto> getAllActiveUsersThatAUserIsFollowing(String username) {
        return null;
    }
}
