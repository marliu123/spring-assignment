package com.cooksys.social_media_api.services.impl;

import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.dtos.UserRequestDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;
import com.cooksys.social_media_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public List<UserResponseDto> getAllNonDeletedUsers() {
        return null;
    }

    @Override
    public UserResponseDto addUser(UserRequestDto user) {
        return null;
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        return null;
    }

    @Override
    public UserResponseDto updateUserByUsername(String username, UserRequestDto user) {
        return null;
    }

    @Override
    public UserResponseDto deleteUserByUsername(String username) {
        return null;
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
