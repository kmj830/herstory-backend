package com.herstory.backend.domain.studio;

import com.herstory.backend.domain.user.User;
import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "artworks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Artwork extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private User artist;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @Builder
    public Artwork(User artist, String title, String description, String imageUrl) {
        this.artist = artist;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
