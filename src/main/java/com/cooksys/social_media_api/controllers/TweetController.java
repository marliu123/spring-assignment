package com.cooksys.social_media_api.controllers;

import com.cooksys.social_media_api.dtos.*;
import com.cooksys.social_media_api.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getAllNonDeletedTweets() {
        return tweetService.getAllNonDeletedTweets();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto addNewTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.addNewTweet(tweetRequestDto);
    }


    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Long id) {
        return tweetService.getTweetById(id);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweetById(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.deleteTweetById(id, credentialsDto);
    }

    @PostMapping("/{id}/like")
    public void likeTweetById(@RequestBody CredentialsDto userCredentials, @PathVariable Long id) {
        tweetService.likeTweetById(userCredentials, id);
    }

    @PostMapping("/{id}/reply")
    public TweetResponseDto  replyToTweetById(@RequestBody TweetRequestDto tweet, @PathVariable Long id) {
        return tweetService.replyToTweetById(tweet, id);
    }

    @PostMapping("/{id}/repost")
    public TweetResponseDto  repostTweetById(@RequestBody CredentialsDto userCredentials, @PathVariable Long id) {
        return tweetService.repostTweetById(userCredentials, id);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getAllHashtagsForSpeciTweet(@PathVariable Long id) {
        return tweetService.getAllHashtagsForSpeciTweet(id);
    }

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getAllUsersThatLikedSpeciTweet(@PathVariable Long id) {
        return tweetService.getAllUsersThatLikedSpeciTweet(id);
    }

    @GetMapping("/{id}/context")
    public ContextDto getTweetContext(@PathVariable Long id) {
        return tweetService.getTweetContext(id);
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getAllRepliesForSpeciTweet(@PathVariable Long id) {
        return tweetService.getAllRepliesForSpeciTweet(id);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getAllRepostsForSpeciTweet(@PathVariable Long id) {
        return tweetService.getAllRepostsForSpeciTweet(id);
    }

    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getAllUsersMentionedInSpeciTweet(@PathVariable Long id) {
        return tweetService.getAllUsersMentionedInSpeciTweet(id);
    }

}
