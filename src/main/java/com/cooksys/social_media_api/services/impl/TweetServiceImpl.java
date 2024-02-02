package com.cooksys.social_media_api.services.impl;

import com.cooksys.social_media_api.dtos.HashtagDto;
import com.cooksys.social_media_api.dtos.TweetRequestDto;
import com.cooksys.social_media_api.dtos.TweetResponseDto;
import com.cooksys.social_media_api.dtos.UserResponseDto;
import com.cooksys.social_media_api.entities.Tweet;
import com.cooksys.social_media_api.entities.User;
import com.cooksys.social_media_api.exceptions.BadRequestException;
import com.cooksys.social_media_api.exceptions.NotFoundException;
import com.cooksys.social_media_api.mappers.TweetMapper;
import com.cooksys.social_media_api.repositories.TweetRepository;
import com.cooksys.social_media_api.repositories.UserRepository;
import com.cooksys.social_media_api.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserRepository userRepository;

    private Tweet getTweet(Long id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty()) {
            throw new NotFoundException("No tweet with id: " + id);
        }
        return tweet.get();
    }

    private void validateTweetRequestDto(TweetRequestDto tweetRequestDto) {

        if (tweetRequestDto.getCredentials() == null
                || tweetRequestDto.getCredentials().getUsername() == null
                || tweetRequestDto.getCredentials().getPassword() == null) {
            throw new BadRequestException("Not authorized");
        }

        Tweet tweet = tweetMapper.requestDtoToEntity(tweetRequestDto);

        if (tweet.getContent() == null) {
            throw new NotFoundException("No content found for tweet with id: " + tweet.getId());
        }

    }

    public List<TweetResponseDto> getAllNonDeletedTweets() {
        return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalse());
    }


    public TweetResponseDto addNewTweet(TweetRequestDto tweetRequestDto) {
        validateTweetRequestDto(tweetRequestDto);

        Tweet tweet = tweetMapper.requestDtoToEntity(tweetRequestDto);
        List<User> users = userRepository.findAllByDeletedFalse();

        for (User u : users) {
            if (u.getCredentials().getUsername() == tweetRequestDto.getCredentials().getUsername()
                    & u.getCredentials().getPassword() == tweetRequestDto.getCredentials().getPassword()) {

                tweet.setAuthor(u);
            }
        }
        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
    }


    public TweetResponseDto getTweetById(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        if (optionalTweet.isEmpty()) {
            throw new NotFoundException("No tweet with id: " + id);
        }
        return tweetMapper.entityToDto(optionalTweet.get());
    }



    public TweetResponseDto deleteTweetById(Long id) {

        Tweet tweetToDelete = getTweet(id);

        List<User> users = userRepository.findAllByDeletedFalse();

        for (User u : users) {
            if (u.getCredentials() == tweetToDelete.getAuthor().getCredentials()
            && u.getTweets().contains(tweetToDelete)) {

                tweetToDelete.setDeleted(true);
                tweetRepository.saveAllAndFlush(u.getTweets());
            } else {
                throw new BadRequestException("Not authorized or no tweet found at id: " + id);
            }
            userRepository.saveAllAndFlush(users);
        }

        return tweetMapper.entityToDto(tweetToDelete);
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
