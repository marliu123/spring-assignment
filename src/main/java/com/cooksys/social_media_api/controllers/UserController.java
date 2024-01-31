package com.cooksys.social_media_api.controllers;


import com.cooksys.social_media_api.dtos.CredentialsDto;
import com.cooksys.social_media_api.dtos.ProfileDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.dtos.UserRequestDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;
import com.cooksys.social_media_api.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAllNonDeletedUsers() {return userService.getAllNonDeletedUsers(); }

    @PostMapping
    public UserResponseDto addUser(@RequestBody UserRequestDto user) {return userService.addUser(user); }

    @GetMapping("/{username}")
    public UserResponseDto getUserByUsername(@PathVariable String username) {return userService.getUserByUsername(username); }

    @PatchMapping("/{username}")
    public UserResponseDto updateUserByUsername(@PathVariable String username, @RequestBody CredentialsDto credentials, @RequestBody ProfileDto profile)
    {return userService.updateUserByUsername(username, credentials, profile);}

    @DeleteMapping("/{username}")
    public UserResponseDto deleteUserByUsername(@PathVariable String username, @RequestBody CredentialsDto credentials) {return userService.deleteUserByUsername(username);}

    @PostMapping("/{username}/follow")
    public void followUserByUsername(@PathVariable String username, @RequestBody UserRequestDto user) {
        return userService.followUserByUsername(username, user);
    }

    @PostMapping("/{username}/unfollow")
    public void unfollowUserByUsername(@PathVariable String username, @RequestBody UserRequestDto user) {
        return userService.unfollowUserByUsername(username, user);
    }

    // this cascades and includes tweets/retweets by followers
//    @GetMapping("/{username}/feed")
//    public List<TweetResponseDto> getAllNonDeletedTweetsByUsername(@PathVariable String username) {
//        return userService.getAllNonDeletedTweetsByUsername(username);
//    }

    @GetMapping("/{username}/tweets")
    public List<TweetResponseDto> getAllNonDeletedTweetsByUsername(@PathVariable String username) {
        return userService.getAllNonDeletedTweetsByUsername(username); }

    @GetMapping("/{username}/mentions")
    public List<TweetResponseDto> getAllTweetsUserIsMentionedIn(@PathVariable String username) {
        return userService.getAllTweetsUserIsMentionedIn(username);
    }

    @GetMapping("/{username}/followers")
    public List<UserResponseDto> getAllActiveFollowersOfUser(@PathVariable String username) {
        return userService.getAllActiveFollowersOfUser(username);
    }

    @GetMapping("/{username}/following")
    public List<UserResponseDto> getAllActiveUsersThatAUserIsFollowing (@PathVariable String username) {
        return userService.getAllActiveUsersThatAUserIsFollowing(username);
    }









}
