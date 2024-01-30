package com.cooksys.social_media_api.services;

import com.cooksys.social_media_api.dtos.HashtagDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;

import java.util.List;

<<<<<<< HEAD
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
=======
public interface HashtagService {
    List<HashtagDto> getAllHashtags();
    public List<TweetResponseDto> getAllTweetsByHashtag(String label);
}
>>>>>>> 60d506feb6ee7d6288f0e2c818e1c31a04dd209c
