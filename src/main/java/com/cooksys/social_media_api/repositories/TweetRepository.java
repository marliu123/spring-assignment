package com.cooksys.social_media_api.repositories;

import com.cooksys.social_media_api.entities.Hashtag;
import com.cooksys.social_media_api.entities.Tweet;
import com.cooksys.social_media_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findByHashtagsIsContaining(Hashtag hashtag);

    List<Tweet> findAllByDeletedFalse();

    List<Tweet> findByAuthorAndDeletedFalse(User user);

}
