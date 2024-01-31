package com.cooksys.social_media_api.entities;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.cooksys.social_media_api.embeddables.Credentials;
import com.cooksys.social_media_api.embeddables.Profile;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_table")
@NoArgsConstructor
@Data
public class User {

	@Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Credentials credentials;

    @Embedded
    private Profile profile;
    
    //################ Changed to @CreationTimestamp so value is not declared until needed #################

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp joined;

    private boolean deleted = false;

    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private List<Tweet> likedTweets = new ArrayList<>();

    @ManyToMany(mappedBy = "mentionedUsers")
    private List<Tweet> mentionedTweets = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "followers_following")
    private List<User> followers;

    @ManyToMany(mappedBy = "followers")
    private List<User> following;

}
