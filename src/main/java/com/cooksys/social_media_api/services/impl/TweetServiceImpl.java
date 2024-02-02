package com.cooksys.social_media_api.services.impl;

import com.cooksys.social_media_api.dtos.*;
import com.cooksys.social_media_api.entities.Hashtag;
import com.cooksys.social_media_api.entities.Tweet;
import com.cooksys.social_media_api.entities.User;
import com.cooksys.social_media_api.exceptions.BadRequestException;
import com.cooksys.social_media_api.exceptions.NotAuthorizedException;
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
            if (u.getCredentials().getUsername().equals(tweetRequestDto.getCredentials().getUsername())
                    & u.getCredentials().getPassword().equals(tweetRequestDto.getCredentials().getPassword())) {

                tweet.setAuthor(u);

//                Matcher matcher = Pattern.compile("(@[^@\\s]*)")
//                        .matcher(tweet.getContent());
//                while (matcher.find()) {
//                    h
//                }
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



    public TweetResponseDto deleteTweetById(Long id, CredentialsDto credentialsDto) {

        Tweet tweetToDelete = tweetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid tweet ID: " + id));

        if (tweetToDelete.isDeleted()) {
            throw new NotFoundException("Tweet not found");
        }

        if (!tweetToDelete.getAuthor().getCredentials().getUsername().equals(credentialsDto.getUsername())
                || !tweetToDelete.getAuthor().getCredentials().getPassword().equals(credentialsDto.getPassword())) {
            throw new NotAuthorizedException("Unauthorized to delete tweet with ID: " +id);
        }

        tweetToDelete.setDeleted(true);
        tweetRepository.save(tweetToDelete);

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

    public List<TweetResponseDto> getTweetsByHashtag(Hashtag hashtag) {
        List<Tweet> tweetsWithHashtag = tweetRepository.findByHashtagsIsContaining(hashtag);
        return tweetMapper.entitiesToDtos(tweetsWithHashtag);
    }
}
