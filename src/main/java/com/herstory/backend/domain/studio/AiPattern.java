package com.herstory.backend.domain.studio;

import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ai_patterns")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiPattern extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id", nullable = false)
    private Artwork artwork;

    @Column(nullable = false)
    private String patternName;

    @Column(nullable = false, length = 2000)
    private String patternImageUrl;


    private String prompt;

    @Builder
    public AiPattern(Artwork artwork, String patternName, String patternImageUrl, String prompt) {
        this.artwork = artwork;
        this.patternName = patternName;
        this.patternImageUrl = patternImageUrl;
        this.prompt = prompt;
    }
}
