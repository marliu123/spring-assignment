package com.cooksys.social_media_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Timestamp posted; // final?

    private String content;

    private Tweet inReplyTo;

    private Tweet repostOf;

    @ManyToOne
    @JoinColumn
    private User author;
}
