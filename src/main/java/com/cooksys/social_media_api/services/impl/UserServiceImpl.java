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
	
	
	private User findUserByUsername(String username) {
		List<User> allUsers = userRepository.findAll();
        for (User u : allUsers) {
            if (u.getCredentials().getUsername().equals(username)) {
                return u;
            } 
        }
        return null;
	}

	private List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<>();
        List<User> allUsers = userRepository.findAll();
        
        for (User user : allUsers) {
            String username = user.getCredentials().getUsername();
            usernames.add(username);
            System.out.println(username);
        }
        
        return usernames;
    }
	
    @Override
    public List<UserResponseDto> getAllNonDeletedUsers() {
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }
    
   
    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
      
    	System.out.println(userRequestDto);
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
    	//return null;
    }

    @Override
    public UserResponseDto updateUserByUsername(String username, ProfileDto profileDto) {
    	User user = userRepository.findByCredentialsUsername(username);
    	if(user == null) {
    		throw new NotFoundException("User not found");
    	}
    	return userMapper.entityToDto(userRepository.saveAndFlush(user)); 
    	//return null;
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
    	
    	userRepository.save(followerUser);
    	userRepository.save(followingUser);
    }

    @Override
    public void unfollowUserByUsername(String username, CredentialsDto credentials) {
    	/*Credentials credential = credentialsMapper.dtoToEntity(credentials);
    	User followerUser = userRepository.findByCredentials(credential);
    	User followingUser = userRepository.findByUsername(username);
    	if(followerUser == null || followingUser == null) {
            throw new NotFoundException("user not found");
        }
    	if(!followingUser.getFollowers().contains(followerUser)) {
    		throw new BadRequestException("no preexisting relationship found");
    	}
    	followerUser.getFollowing().remove(followingUser);
    	followingUser.getFollowers().remove(followerUser);
    	userRepository.save(followerUser);
    	userRepository.save(followingUser); */
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
