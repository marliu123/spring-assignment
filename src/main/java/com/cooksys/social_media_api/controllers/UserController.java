package com.cooksys.social_media_api.controllers;


import com.cooksys.social_media_api.dtos.CredentialsDto;
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

    @GetMapping("/@{username}")
    public UserResponseDto getUserByUsername(@PathVariable String username) {return userService.getUserByUsername(username); }

    @PatchMapping("/@{username}")
    public UserResponseDto updateUserByUsername(@PathVariable String username, @RequestBody UserRequestDto userRequestDto)
    {return userService.updateUserByUsername(username, userRequestDto);}

    @DeleteMapping("/@{username}")
    public UserResponseDto deleteUserByUsername(@PathVariable String username) {return userService.deleteUserByUsername(username);}

    @PostMapping("/@{username}/follow")
    public void followUserByUsername(@PathVariable String username, @RequestBody CredentialsDto credentials) {
        userService.followUserByUsername(username, credentials);
    }

    @PostMapping("/@{username}/unfollow")
    public void unfollowUserByUsername(@PathVariable String username, @RequestBody CredentialsDto credentials) {
        userService.unfollowUserByUsername(username, credentials);
    }

    @GetMapping("/@{username}/feed")
    List<TweetResponseDto> getFeed(@PathVariable String username) {
        return userService.getFeed(username);
    }
    @GetMapping("/@{username}/tweets")
    public List<TweetResponseDto> getAllNonDeletedTweetsByUser(@PathVariable String username) {
        return userService.getAllNonDeletedTweetsByUser(username); }

    @GetMapping("/@{username}/mentions")
    public List<TweetResponseDto> getAllTweetsUserIsMentionedIn(@PathVariable String username) {
        return userService.getAllTweetsUserIsMentionedIn(username);
    }

    // me
    @GetMapping("/@{username}/followers")
    public List<UserResponseDto> getAllActiveFollowersOfUser(@PathVariable String username) {
        return userService.getAllActiveFollowersOfUser(username);
    }

    //me
    @GetMapping("/@{username}/following")
    public List<UserResponseDto> getAllActiveUsersThatAUserIsFollowing (@PathVariable String username) {
        return userService.getAllActiveUsersThatAUserIsFollowing(username);
    }









}
