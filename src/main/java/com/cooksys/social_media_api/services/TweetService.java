package com.cooksys.social_media_api.services;

import com.cooksys.social_media_api.dtos.*;

import java.util.List;

public interface TweetService {

    List<TweetResponseDto> getAllNonDeletedTweets();
    TweetResponseDto addNewTweet(TweetRequestDto tweetRequestDto);
    TweetResponseDto getTweetById(Long id);
    TweetResponseDto deleteTweetById(Long id);
    TweetResponseDto replyToTweetById(Long id, TweetRequestDto tweet);
    TweetResponseDto repostTweetById(Long id);
    List<HashtagDto> getAllHashtagsForSpeciTweet(Long id);
    List<UserResponseDto> getAllUsersThatLikedSpeciTweet(Long id);
    List<TweetResponseDto> getAllRepliesForSpeciTweet(Long id);
    List<TweetResponseDto> getAllRepostsForSpeciTweet(Long id);
    List<UserResponseDto> getAllUsersMentionedInSpeciTweet(Long id);

}
