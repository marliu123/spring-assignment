package com.cooksys.social_media_api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@Entity
@NoArgsConstructor
@Data
public class Hashtag {


    @Id
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp firstUsed; // final?

    @Column
    @UpdateTimestamp
    private Timestamp lastUsed;


}
