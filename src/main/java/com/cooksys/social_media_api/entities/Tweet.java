package com.cooksys.social_media_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp posted;

    private boolean isDeleted;

    private String content;

    @ManyToOne
    @JoinColumn
    private User author;

    @OneToOne
    private Tweet inReplyTo;

    @OneToOne
    private Tweet repostOf;

    @OneToMany
    @JoinTable (
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private List<Hashtag> hashtags;


}
