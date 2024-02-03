package com.cooksys.social_media_api.services;

import com.cooksys.social_media_api.dtos.HashtagDto;
import com.cooksys.social_media_api.dtos.TweetRequestDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;
import com.cooksys.social_media_api.entities.Hashtag;
import com.cooksys.social_media_api.dtos.*;
import com.cooksys.social_media_api.entities.User;


import java.util.List;

public interface TweetService {

    List<TweetResponseDto> getAllNonDeletedTweets();
    TweetResponseDto addNewTweet(TweetRequestDto tweetRequestDto);
    TweetResponseDto getTweetById(Long id);
    TweetResponseDto deleteTweetById(Long id, CredentialsDto credentialsDto);
    TweetResponseDto replyToTweetById(TweetRequestDto tweet, Long id);
    TweetResponseDto repostTweetById(CredentialsDto userCredentials, Long id);
    List<HashtagDto> getAllHashtagsForSpeciTweet(Long id);
    List<UserResponseDto> getAllUsersThatLikedSpeciTweet(Long id);
    List<TweetResponseDto> getAllRepliesForSpeciTweet(Long id);
    List<TweetResponseDto> getAllRepostsForSpeciTweet(Long id);
    List<UserResponseDto> getAllUsersMentionedInSpeciTweet(Long id);
    List<TweetResponseDto> getTweetsByHashtag(Hashtag hashtag);
    List<TweetResponseDto> getAllNonDeletedTweetsByUser(User user);
    List<TweetResponseDto> getAllTweetsUserIsMentionedIn(User user);
    void likeTweetById(CredentialsDto userCredentials, Long id);
    ContextDto getTweetContext(Long id);

}
