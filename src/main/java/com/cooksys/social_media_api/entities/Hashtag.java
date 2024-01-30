package com.cooksys.social_media_api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Entity
@NoArgsConstructor
@Data
public class Hashtag {


    @Id
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private Timestamp firstUsed; // final?

    @Column(nullable = false)
    private Timestamp lastUsed;


}
