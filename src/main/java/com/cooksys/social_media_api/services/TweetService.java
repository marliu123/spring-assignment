package com.cooksys.social_media_api.services;

import com.cooksys.social_media_api.dtos.HashtagDto;
import com.cooksys.social_media_api.dtos.TweetRequestDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;

import java.util.List;

public interface TweetService {

    public List<TweetResponseDto> getAllNonDeletedTweets();
    public TweetResponseDto addNewTweet(TweetRequestDto tweet);
    public TweetResponseDto getTweetById(Long id);
    public TweetResponseDto deleteTweetById(Long id);
    public TweetResponseDto replyToTweetById(Long id, TweetRequestDto tweet);
    public TweetResponseDto repostTweetById(Long id);
    public List<HashtagDto> getAllHashtagsForSpeciTweet(Long id);
    public List<UserResponseDto> getAllUsersThatLikedSpeciTweet(Long id);
    public List<TweetResponseDto> getAllRepliesForSpeciTweet(Long id);
    public List<TweetResponseDto> getAllRepostsForSpeciTweet(Long id);
    public List<UserResponseDto> getAllUsersMentionedInSpeciTweet(Long id);

}
