package com.cooksys.social_media_api.services.impl;


import com.cooksys.social_media_api.embeddables.Credentials;
import com.cooksys.social_media_api.embeddables.Profile;
import com.cooksys.social_media_api.entities.User;
import com.cooksys.social_media_api.exceptions.BadRequestException;
import com.cooksys.social_media_api.exceptions.NotFoundException;
import com.cooksys.social_media_api.dtos.*;
import com.cooksys.social_media_api.mappers.CredentialsMapper;
import com.cooksys.social_media_api.mappers.ProfileMapper;
import com.cooksys.social_media_api.mappers.UserMapper;
import com.cooksys.social_media_api.repositories.UserRepository;
import com.cooksys.social_media_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final ProfileMapper profileMapper;
	private final CredentialsMapper credentialsMapper;
	
	
	
    @Override
    public List<UserResponseDto> getAllNonDeletedUsers() {
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }
    
   
    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
    	if(userRequestDto == null){
            throw new BadRequestException("User request is null");
        }

        CredentialsDto credentialsDto = userRequestDto.getCredentials();
        ProfileDto profileDto = userRequestDto.getProfile();

        if(credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null){
            throw new BadRequestException("Credentials are invalid");
        }

        if(profileDto == null || profileDto.getEmail() == null){
            throw new BadRequestException("Profile is invalid");
        }

        if(userRepository.findByCredentialsUsername(credentialsDto.getUsername()) != null){
            throw new BadRequestException("User already exists");
        }
    	User user = userMapper.requestDtoToEntity(userRequestDto);
    	user.setDeleted(false);
    	return userMapper.entityToDto(userRepository.saveAndFlush(user));
    	//return null
    	
    	
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {

		User user = userRepository.findByCredentialsUsername(username);
    	if(user == null) {
    		throw new NotFoundException("User not found with username: "+username);
    	}
        return userMapper.entityToDto(user); 
    }

    @Override
    public UserResponseDto updateUserByUsername(String username, UserRequestDto userRequestDto) {
    	User user = userRepository.findByCredentialsUsername(username);
    	User user2 = userMapper.requestDtoToEntity(userRequestDto);
    	if(user == null) {
    		throw new NotFoundException("User not found");
    	}
    	if(user2.getCredentials() == null || user2.getProfile() == null || user2.getCredentials().getUsername() == null || user2.getCredentials().getPassword() == null) {
    		throw new BadRequestException("Invalid");
    	}
    	if(user2.getProfile().getFirstName() != null) {
    		user.getProfile().setFirstName(user2.getProfile().getFirstName());
    	}
    	if(user2.getProfile().getLastName() != null) {
    		user.getProfile().setLastName(user2.getProfile().getLastName());
    	}
    	if(user2.getProfile().getPhone() != null) {
    		user.getProfile().setPhone(user2.getProfile().getPhone());
    	}
    	if(user2.getProfile().getEmail() != null) {
    		user.getProfile().setEmail(user2.getProfile().getEmail());
    	}
    	return userMapper.entityToDto(userRepository.saveAndFlush(user)); 
    }

    @Override
    public UserResponseDto deleteUserByUsername(String username) {
    	User user = userRepository.findByCredentialsUsername(username);
    	if(user == null) {
    		throw new NotFoundException("User not found");
    	}
    	user.setDeleted(true);
    	return userMapper.entityToDto(userRepository.saveAndFlush(user)); 
    	//return null;
    }

    @Override
    public void followUserByUsername(String username, CredentialsDto credentials) {
    	Credentials credential = credentialsMapper.dtoToEntity(credentials);
    	// user that is going to follow 
    	User followerUser = userRepository.findByCredentials(credential);
    	System.out.println(credentials);
    	// user that is being followed
    	User followingUser = userRepository.findByCredentialsUsername(username);
    	if(followerUser == null || followingUser == null) {
            throw new NotFoundException("user not found");
        }
    	if(followingUser.getFollowers().contains(followerUser)) {
    		throw new BadRequestException("This user is already following "+username);
    	}
    	// adding to the following / followed for each user
    	followerUser.getFollowing().add(followingUser);
    	followingUser.getFollowers().add(followerUser);
    	userRepository.saveAndFlush(followerUser);
    	userRepository.saveAndFlush(followingUser);
    }

    @Override
    public void unfollowUserByUsername(String username, CredentialsDto credentials) {
    	Credentials credential = credentialsMapper.dtoToEntity(credentials);
    	User followerUser = userRepository.findByCredentials(credential);
    	User followingUser = userRepository.findByCredentialsUsername(username);
    	if(followerUser == null || followingUser == null) {
            throw new NotFoundException("user not found");
        }
    	if(!followingUser.getFollowers().contains(followerUser)) {
    		throw new BadRequestException("no preexisting relationship found");
    	}
    	followerUser.getFollowing().remove(followingUser);
    	followingUser.getFollowers().remove(followerUser);
    	userRepository.saveAndFlush(followerUser);
    	userRepository.saveAndFlush(followingUser); 
    }

    @Override
    public List<TweetResponseDto> getAllNonDeletedTweetsByUsername(String username) {
    	//User user = userRepository.findByUsername(username);
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
