package com.cooksys.social_media_api.services.impl;

import com.cooksys.social_media_api.dtos.*;
import com.cooksys.social_media_api.entities.Hashtag;
import com.cooksys.social_media_api.entities.Tweet;
import com.cooksys.social_media_api.entities.User;
import com.cooksys.social_media_api.exceptions.BadRequestException;
import com.cooksys.social_media_api.exceptions.NotFoundException;
import com.cooksys.social_media_api.mappers.TweetMapper;
import com.cooksys.social_media_api.repositories.TweetRepository;
import com.cooksys.social_media_api.repositories.UserRepository;
import com.cooksys.social_media_api.services.TweetService;
import com.cooksys.social_media_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserService userService;
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

    public void likeTweetById(CredentialsDto userCredentials, Long id) {
        User userToLike = userService.validateUserCredentials(userCredentials);
        List<Tweet> usersLikedTweets = userToLike.getLikedTweets();
        Optional<Tweet> tweetToLike = tweetRepository.findById(id);
        if (tweetToLike.isPresent()) {
            usersLikedTweets.add(tweetToLike.get());
            userToLike.setLikedTweets(usersLikedTweets);
            userService.updateUserLikedTweets(userToLike);
        }
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

    public List<TweetResponseDto> getAllNonDeletedTweetsByUser(User user) {
        List<Tweet> allNonDelByUser = tweetRepository.findByAuthorAndDeletedFalse(user);
        return tweetMapper.entitiesToDtos(allNonDelByUser);
    }

    public List<TweetResponseDto> getAllTweetsUserIsMentionedIn(User user) {
        List<Tweet> tweetsUserIsMentionedIn = tweetRepository.findByDeletedFalseAndMentionedUsersContaining(user);
        return tweetMapper.entitiesToDtos(tweetsUserIsMentionedIn);
    }
}
