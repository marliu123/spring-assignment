package com.cooksys.social_media_api.services;

import com.cooksys.social_media_api.dtos.CredentialsDto;
import com.cooksys.social_media_api.dtos.ProfileDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.dtos.UserRequestDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;
import com.cooksys.social_media_api.embeddables.Credentials;
import com.cooksys.social_media_api.entities.User;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllNonDeletedUsers();

    UserResponseDto addUser(UserRequestDto user);

    UserResponseDto getUserByUsername(String username);

    UserResponseDto updateUserByUsername(String username, UserRequestDto userRequestDto);

    UserResponseDto deleteUserByUsername(String username);

    void followUserByUsername(String username, CredentialsDto credentials);

    void unfollowUserByUsername(String username, CredentialsDto credentials);

    List<TweetResponseDto> getAllNonDeletedTweetsByUsername(String username);

    List<TweetResponseDto> getAllTweetsUserIsMentionedIn(String username);

    List<UserResponseDto> getAllActiveFollowersOfUser(String username);

    List<UserResponseDto> getAllActiveUsersThatAUserIsFollowing(String username);

    User validateUserCredentials(CredentialsDto userCredentials);
    void updateUserLikedTweets(User user);
}
