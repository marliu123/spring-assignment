package com.cooksys.social_media_api.services;

import com.cooksys.social_media_api.dtos.HashtagDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.entities.Hashtag;
import com.cooksys.social_media_api.mappers.HashtagMapper;
import com.cooksys.social_media_api.repositories.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

    public List<HashtagDto> getAllHashtags() {
        return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());}

    public List<TweetResponseDto> getAllTweetsByHashtag(String label) {
        return hashtagRepository.getByLabel(label);}
}