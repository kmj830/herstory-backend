package com.herstory.backend.domain.showroom;

import com.herstory.backend.domain.studio.AiPattern;
import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "showroom_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowroomItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_pattern_id", nullable = false)
    private AiPattern aiPattern;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;

    private String rendering3dUrl;

    @Column(nullable = false)
    private Long sponsorCount = 0L;

    @Column(nullable = false)
    private BigDecimal totalSponsorshipAmount = BigDecimal.ZERO;

    @Builder
    public ShowroomItem(AiPattern aiPattern, String title, BigDecimal price, String description, String rendering3dUrl) {
        this.aiPattern = aiPattern;
        this.title = title;
        this.price = price;
        this.description = description;
        this.rendering3dUrl = rendering3dUrl;
    }

    public void addSponsorship(BigDecimal amount) {
        this.sponsorCount++;
        this.totalSponsorshipAmount = this.totalSponsorshipAmount.add(amount);
    }
}
