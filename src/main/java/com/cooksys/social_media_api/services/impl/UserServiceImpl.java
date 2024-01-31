package com.cooksys.social_media_api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.social_media_api.dtos.CredentialsDto;
import com.cooksys.social_media_api.dtos.ProfileDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.dtos.UserRequestDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;
import com.cooksys.social_media_api.entities.User;
import com.cooksys.social_media_api.mappers.CredentialsMapper;
import com.cooksys.social_media_api.mappers.ProfileMapper;
import com.cooksys.social_media_api.mappers.UserMapper;
import com.cooksys.social_media_api.repositories.UserRepository;
import com.cooksys.social_media_api.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final ProfileMapper profileMapper;
	private final CredentialsMapper credentialsMapper;
	
    @Override
    public List<UserResponseDto> getAllNonDeletedUsers() {
//        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    	return null;
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
//    	User user = userRepository.findByUsername(username);
//    	if(user == null) {
//    		throw new NotFoundException("User not found with username: "+username);
//    	}
//    	
//        return userMapper.entityToDto(user);
    	return null;
    }

    @Override
    public UserResponseDto updateUserByUsername(String username, CredentialsDto credentials, ProfileDto profile) {
//    	User user = userRepository.findByUsername(username);
//    	Profile savedProfile = profileMapper.dtoToEntity(profile);
//    	if(user == null || user.getCredentials().getUsername() != credentials.getUsername() || user.getCredentials().getPassword() != credentials.getPassword()) {
//    		throw new NotFoundException("User not found");
//    	}
//    	user.setProfile(savedProfile);
//    	return userMapper.entityToDto(userRepository.saveAndFlush(user));
    	return null;
    }

    @Override
    public UserResponseDto deleteUserByUsername(String username, CredentialsDto credentials) {
//    	User user = userRepository.findByUsername(username);
//    	if(user == null || user.getCredentials().getUsername() != credentials.getUsername() || user.getCredentials().getPassword() != credentials.getPassword()) {
//    		throw new NotFoundException("User not found");
//    	}
//    	user.setDeleted(true);
//    	return userMapper.entityToDto(userRepository.saveAndFlush(user)); 
    	return null;
    }

    @Override
    public void followUserByUsername(String username, CredentialsDto credentials) {
//    	Credentials credential = credentialsMapper.dtoToEntity(credentials);
//    	// user that is going to follow 
//    	User followerUser = userRepository.findByCredentials(credential);
//    	// user that is being followed
//    	User followingUser = userRepository.findByUsername(username);
//    	if(followerUser == null || followingUser == null) {
//            throw new NotFoundException("user not found");
//        }
//    	if(followingUser.getFollowers().contains(followerUser)) {
//    		throw new BadRequestException("This user is already following "+username);
//    	}
//    	// adding to the following / followed for each user
//    	followerUser.getFollowing().add(followingUser);
//    	followingUser.getFollowers().add(followerUser);
//    	
//    	userRepository.save(followerUser);
//    	userRepository.save(followingUser);
    }

    @Override
    public void unfollowUserByUsername(String username, CredentialsDto credentials) {
//    	Credentials credential = credentialsMapper.dtoToEntity(credentials);
//    	User followerUser = userRepository.findByCredentials(credential);
//    	User followingUser = userRepository.findByUsername(username);
//    	if(followerUser == null || followingUser == null) {
//            throw new NotFoundException("user not found");
//        }
//    	if(!followingUser.getFollowers().contains(followerUser)) {
//    		throw new BadRequestException("no preexisting relationship found");
//    	}
//    	followerUser.getFollowing().remove(followingUser);
//    	followingUser.getFollowers().remove(followerUser);
//    	userRepository.save(followerUser);
//    	userRepository.save(followingUser);
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
