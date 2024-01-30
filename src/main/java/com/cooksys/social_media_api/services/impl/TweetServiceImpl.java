package com.cooksys.social_media_api.services.impl;

import com.cooksys.social_media_api.dtos.HashtagDto;
import com.cooksys.social_media_api.dtos.TweetRequestDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;
import com.cooksys.social_media_api.entities.Tweet;
import com.cooksys.social_media_api.mappers.TweetMapper;
import com.cooksys.social_media_api.repositories.TweetRepository;
import com.cooksys.social_media_api.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    public List<TweetResponseDto> getAllNonDeletedTweets() {
        return tweetMapper.entitiesToDtos(tweetRepository.findAll());
    }

    public TweetResponseDto addNewTweet(TweetRequestDto tweet) {
        Tweet tweetToAdd = tweetMapper.requestDtoToEntity(tweet);
        Tweet addedTweet = tweetRepository.save(tweetToAdd);
        return tweetMapper.entityToDto(addedTweet);
    }

    public TweetResponseDto getTweetById(Long id)
    { return tweetMapper.entityToDto(tweetRepository.getById(id));}

    public TweetResponseDto deleteTweetById(Long id) {
//        return tweetMapper.entityToDto(tweetRepository.deleteById(id));
        return null;
    }

    public void likeTweetById(Long id) {//add/send like to tweet by ID}
    }

    public TweetResponseDto replyToTweetById(Long id, TweetRequestDto tweet) {
//        return tweetMapper.entityToDto(tweetRepository.replyToTweetById(id, tweet));
        return null;
    }

    public TweetResponseDto repostTweetById(Long id) {
//        return tweetMapper.entityToDto(tweetRepository.repostTweetById(id));
        return null;
    }

    public List<HashtagDto> getAllHashtagsForSpeciTweet(Long id) {
        // return get all from hashtag service or repo?
        return null;
    }

    public List<UserResponseDto> getAllUsersThatLikedSpeciTweet(Long id) {
        //return get all from user service or repo?
        return null;
    }

    public List<TweetResponseDto> getAllRepliesForSpeciTweet(Long id) {
//        return tweetMapper.entitiesToDtos(tweetRepository.getAllRepliesForSpeciTweet(id));
        //can just get the tweet and iterate through its replies

        return null;
    }

    public List<TweetResponseDto> getAllRepostsForSpeciTweet(Long id) {
//        return tweetMapper.entitiesToDtos(tweetRepository.getAllRepostsForSpeciTweet(id));
        //can just get the tweet and iterate through its reposts

        return null;
    }

    public List<UserResponseDto> getAllUsersMentionedInSpeciTweet(Long id) {
//        return tweetMapper.entitiesToDtos(tweetRepository.getAllUsersMentionedInSpeciTweet(id));
        //can just get the tweet, iterate through mentions in body

        return null;
    }
}
