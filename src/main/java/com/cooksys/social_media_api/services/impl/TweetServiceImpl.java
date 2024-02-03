package com.cooksys.social_media_api.services.impl;

import com.cooksys.social_media_api.dtos.*;
import com.cooksys.social_media_api.embeddables.Credentials;
import com.cooksys.social_media_api.entities.Hashtag;
import com.cooksys.social_media_api.entities.Tweet;
import com.cooksys.social_media_api.entities.User;
import com.cooksys.social_media_api.exceptions.BadRequestException;
import com.cooksys.social_media_api.exceptions.NotAuthorizedException;
import com.cooksys.social_media_api.exceptions.NotFoundException;
import com.cooksys.social_media_api.mappers.HashtagMapper;
import com.cooksys.social_media_api.mappers.CredentialsMapper;
import com.cooksys.social_media_api.mappers.TweetMapper;
import com.cooksys.social_media_api.repositories.HashtagRepository;
import com.cooksys.social_media_api.mappers.UserMapper;
import com.cooksys.social_media_api.repositories.TweetRepository;
import com.cooksys.social_media_api.repositories.UserRepository;
import com.cooksys.social_media_api.services.TweetService;
import com.cooksys.social_media_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final HashtagRepository hashtagRepository;
    private final TweetMapper tweetMapper;
    private final CredentialsMapper credentialsMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HashtagMapper hashtagMapper;

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

        Credentials credentials = credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials());

        User user = userRepository.findByCredentials(credentials);

        tweet.setAuthor(user);
        tweet.setDeleted(false);

        String[] splitContent = tweet.getContent().split(" ");

        ArrayList<User> mentions = new ArrayList<>();

        for (String s : splitContent) {
            if (s.charAt(0) == '@') {
                String mention = s.substring(1);
                User mentionedUser = userRepository.findByCredentialsUsername(mention);

                if (mentionedUser != null) {
                    mentions.add(mentionedUser);
                }
            }
        }

        tweet.setMentionedUsers(mentions);

        ArrayList<Hashtag> hashtags = new ArrayList<>();

        for (String s : splitContent) {
            if (s.charAt(0) == '#') {
                String label = s.substring(1);

                Hashtag hashtag = hashtagRepository.findByLabel(label);

                if (hashtag != null) {
                    hashtag.getTweets().add(tweet);
                    hashtagRepository.saveAndFlush(hashtag);
                } else {
                    Hashtag newHashtag = new Hashtag();
                    ArrayList<Tweet> tweets = new ArrayList<>();

                    tweets.add(tweet);
                    newHashtag.setLabel(label);
                    newHashtag.setTweets(tweets);
                    hashtags.add(newHashtag);

                    hashtagRepository.saveAndFlush(newHashtag);
                }
            }
        }
        tweet.setHashtags(hashtags);
        tweetRepository.saveAndFlush(tweet);

        return tweetMapper.entityToDto(tweet);
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


    public void likeTweetById(CredentialsDto userCredentials, Long id) {
        User userToLike = userService.validateUserCredentials(userCredentials);
        if (!userToLike.isDeleted()) {
            List<Tweet> usersLikedTweets = userToLike.getLikedTweets();
            Optional<Tweet> tweetToLike = tweetRepository.findById(id);
            if (tweetToLike.isPresent() && !tweetToLike.get().getLikedByUsers().contains(userToLike)) {
                usersLikedTweets.add(tweetToLike.get());
                userToLike.setLikedTweets(usersLikedTweets);
                userService.updateUserLikedTweets(userToLike);
            }
        }
    }

    public TweetResponseDto replyToTweetById(TweetRequestDto tweet, Long id) {
        Credentials credentials = credentialsMapper.dtoToEntity(tweet.getCredentials());
        String content = tweet.getContent();
        User user = userRepository.findByCredentials(credentials);
        if (user != null) {
            Optional<Tweet> tweetToReplyTo = tweetRepository.findById(id);
            if (tweetToReplyTo.isPresent()) {
                Tweet newTweet = new Tweet();
                newTweet.setAuthor(user);
                newTweet.setDeleted(false);
                newTweet.setContent(content);
                newTweet.setInReplyTo(tweetToReplyTo.get());

                String[] splitContent = content.split(" ");

                ArrayList<User> mentions = new ArrayList<>();

                for (String s : splitContent) {
                    if (s.charAt(0) == '@') {
                        String mention = s.substring(1);
                        User mentionedUser = userRepository.findByCredentialsUsername(mention);

                        if (mentionedUser != null) {
                            mentions.add(mentionedUser);
                        }
                    }
                }

                newTweet.setMentionedUsers(mentions);

                ArrayList<Hashtag> hashtags = new ArrayList<>();

                for (String s : splitContent) {
                    if (s.charAt(0) == '#') {
                        String label = s.substring(1);

                        Hashtag hashtag = new Hashtag();

                        if (hashtagRepository.findByLabel(label) != null) {
                            hashtag.getTweets().add(newTweet);
                            hashtagRepository.saveAndFlush(hashtag);
                        } else {
                            ArrayList<Tweet> tweets = new ArrayList<>();
                            tweets.add(newTweet);
                            hashtag.setLabel(label);
                            hashtag.setTweets(tweets);
                            hashtagRepository.saveAndFlush(hashtag);
                        }
                    }
                }

               List<Tweet> newReplies = tweetToReplyTo.get().getReplies();

                Tweet savedReplyTweet = tweetRepository.save(newTweet);
               newReplies.add(savedReplyTweet);

               tweetToReplyTo.get().setReplies(newReplies);

               return tweetMapper.entityToDto(savedReplyTweet);

            }
        }
        return null;
    }

    public TweetResponseDto repostTweetById(CredentialsDto userCredentials, Long id) {
        User userToRepost = userService.validateUserCredentials(userCredentials);
        Optional<Tweet> tweetToRepost = tweetRepository.findById(id);
        if (tweetToRepost.isPresent()) {
            Tweet repostedTweet = new Tweet();
            repostedTweet.setAuthor(userToRepost);
            repostedTweet.setDeleted(false);
            repostedTweet.setContent(tweetToRepost.get().getContent());
            repostedTweet.setReplies(new ArrayList<>(tweetToRepost.get().getReplies()));
//            repostedTweet.setInReplyTo(tweetToRepost.get());
            repostedTweet.setRepostOf(tweetToRepost.get());
            repostedTweet.setHashtags(new ArrayList<>(tweetToRepost.get().getHashtags()));
            repostedTweet.setLikedByUsers(new ArrayList<>(tweetToRepost.get().getLikedByUsers()));
            repostedTweet.setMentionedUsers(new ArrayList<>(tweetToRepost.get().getMentionedUsers()));

            repostedTweet = tweetRepository.saveAndFlush(repostedTweet);

            return tweetMapper.entityToDto(repostedTweet);
        }
        return null;
    }

    public List<HashtagDto> getAllHashtagsForSpeciTweet(Long id) {
    	if(tweetRepository.findById(id) == null) {
    		throw new NotFoundException("no tweet with id "+id);
    	}
    	Optional<Tweet> tweet = tweetRepository.findById(id);
    	List<Hashtag> list = tweet.get().getHashtags();
        return hashtagMapper.entitiesToDtos(list); 
    }

    public List<UserResponseDto> getAllUsersThatLikedSpeciTweet(Long id) {
        Optional<Tweet> tweetToLike = tweetRepository.findById(id);
    	if(tweetToLike.isEmpty() || tweetToLike.get().isDeleted()) {
    		throw new NotFoundException("no tweet with id "+id);
    	}
        List<User> usersThatLiked = tweetToLike.get().getLikedByUsers();
        List<User> activeUsersThatLiked = new ArrayList<>();

        for (User user : usersThatLiked) {
            if (!user.isDeleted()) {
                activeUsersThatLiked.add(user);
            }
        }
        return userMapper.entitiesToDtos(activeUsersThatLiked);
    }

    public List<TweetResponseDto> getAllRepliesForSpeciTweet(Long id) {
    	if(tweetRepository.findById(id) == null) {
    		throw new NotFoundException("no tweet with id "+id);
    	}
    	Optional<Tweet> tweet = tweetRepository.findById(id);
    	List<Tweet> list = tweet.get().getReplies();
    	List<Tweet> list2 = new ArrayList<>();
    	for(Tweet t: list) {
    		if(t.getInReplyTo().getAuthor().isDeleted() == false) {
    			list2.add(t);
    		}
    	}
    	return tweetMapper.entitiesToDtos(list2); 
    }

    public List<TweetResponseDto> getAllRepostsForSpeciTweet(Long id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isPresent()) {
            return tweetMapper.entitiesToDtos( tweet.get().getReposts());
        }
        return null;
    }

    public List<UserResponseDto> getAllUsersMentionedInSpeciTweet(Long id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isPresent()) {
            return userMapper.entitiesToDtos(tweet.get().getMentionedUsers());
        }

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

    public ContextDto getTweetContext(Long id) {
        Optional<Tweet> target = tweetRepository.findById(id);
        if (target.isPresent() && !target.get().isDeleted()) {
            List<Tweet> before = getBeforeTweetChain(target.get());
            List<Tweet> after = getAfterTweetChain(target.get());

            ContextDto tweetContext = new ContextDto();
            tweetContext.setTarget(tweetMapper.entityToDto(target.get()));
            tweetContext.setBefore(tweetMapper.entitiesToDtos(before));
            tweetContext.setAfter(tweetMapper.entitiesToDtos(after));

            return tweetContext;
        }
        throw new NotFoundException("This tweet does not exist and does not have a context");
    }

    public List<Tweet> getBeforeTweetChain(Tweet tweet) {
        List<Tweet> beforeChain = new ArrayList<>();

        if (tweet.getInReplyTo() != null) {
            beforeChain.add(tweet.getInReplyTo());
            beforeChain.addAll(getBeforeTweetChain(tweet.getInReplyTo()));
        }

        return beforeChain;
    }

    public List<Tweet> getAfterTweetChain(Tweet tweet) {
        List<Tweet> afterChain = new ArrayList<>(); 
        for (Tweet t : tweet.getReplies()){
            if (!t.isDeleted()) {
            afterChain.add(t);
            }
            afterChain.addAll(getAfterTweetChain(t));
        }
        afterChain.sort(new Comparator<Tweet>() {
            @Override
            public int compare(Tweet o1, Tweet o2) {
                return o2.getPosted().compareTo(o1.getPosted());
            }
        });
        return afterChain;
    }
}