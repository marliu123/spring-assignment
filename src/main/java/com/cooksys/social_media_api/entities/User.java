package com.cooksys.social_media_api.entities;


import com.cooksys.social_media_api.embeddables.Credentials;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Credentials credentials;

    @Column(nullable = false)
    private Timestamp joined;// final?

    @Embedded
    private Profile profile;

}
