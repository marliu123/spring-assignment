package com.cooksys.social_media_api.services.impl;

import com.cooksys.social_media_api.dtos.HashtagDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.entities.Hashtag;
import com.cooksys.social_media_api.exceptions.NotFoundException;
import com.cooksys.social_media_api.mappers.HashtagMapper;
import com.cooksys.social_media_api.mappers.TweetMapper;
import com.cooksys.social_media_api.repositories.HashtagRepository;
import com.cooksys.social_media_api.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;
    private final TweetMapper tweetMapper;

    public List<HashtagDto> getAllHashtags() {
        return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
    }

    public List<TweetResponseDto> getAllTweetsByHashtag(String label) {
        Hashtag hashtag = hashtagRepository.findByLabel("#" + label);

        if (hashtag == null) {
            throw new NotFoundException("hashtag is null");
        }

        // Cannot invoke "com.cooksys.social_media_api.entities.Hashtag.getTweets()"
            // because "hashtag" is null

        return tweetMapper.entitiesToDtos(hashtag.getTweets());
    }
}
