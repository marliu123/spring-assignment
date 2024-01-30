package com.cooksys.social_media_api.services;

import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.dtos.UserRequestDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllNonDeletedUsers();

    UserResponseDto addUser(UserRequestDto user);

    UserResponseDto getUserByUsername(String username);

    UserResponseDto updateUserByUsername(String username, UserRequestDto user);

    UserResponseDto deleteUserByUsername(String username);

    void followUserByUsername(String username, UserRequestDto user);

    void unfollowUserByUsername(String username, UserRequestDto user);

    List<TweetResponseDto> getAllNonDeletedTweetsByUsername(String username);

    List<TweetResponseDto> getAllTweetsUserIsMentionedIn(String username);

    List<UserResponseDto> getAllActiveFollowersOfUser(String username);

    List<UserResponseDto> getAllActiveUsersThatAUserIsFollowing(String username);
}
