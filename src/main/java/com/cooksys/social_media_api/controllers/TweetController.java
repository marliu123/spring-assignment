package com.cooksys.social_media_api.controllers;

import com.cooksys.social_media_api.dtos.HashtagDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    @GetMapping
    public List<TweetResponseDto> getAllNonDeletedTweets() { return tweetService.getAllNonDeletedTweets(); }

    @PostMapping
    public TweetResponseDto addNewTweet() { return tweetService.addNewTweet(); }

    @GetMapping("/{id}")
    public TweetResponseDto getTweetById() { return tweetService.getTweetById(); }

    @DeleteMapping("/{id")
    public TweetResponseDto deleteTweetById() { return tweetService.deleteTweetById(); }

    @PostMapping("/{id}/like")
    public void likeTweetById() { return tweetService.likeTweetById(); }

    @PostMapping("/{id}/reply")
    public TweetResponseDto  replyToTweetById() { return tweetService.replyToTweetById(); }

    @PostMapping("/{id}/repost")
    public TweetResponseDto  repostTweetById() { return tweetService.repostTweetById(); }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getAllHashtagsForSpeciTweet() { return tweetService.getAllHashtagsForSpeciTweet(); }

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getAllUsersThatLikedSpeciTweet() { return tweetService.getAllUsersThatLikedSpeciTweet();}

    //get context mapping by id

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getAllRepliesForSpeciTweet() { return tweetService.getAllRepliesForSpeciTweet(); }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getAllRepostsForSpeciTweet() {return tweetService.getAllRepostsForSpeciTweet(); }

    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getAllUsersMentionedInSpeciTweet() {return tweetService.getAllUsersMentionedInSpeciTweet(); }

}
