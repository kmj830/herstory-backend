package com.herstory.backend.domain.o2o;

import com.herstory.backend.domain.showroom.CustomDesign;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "media_wall_posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MediaWallPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_design_id")
    private CustomDesign customDesign;

    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DisplayStatus displayStatus;

    @Builder
    public MediaWallPost(User user, CustomDesign customDesign, String message, DisplayStatus displayStatus) {
        this.user = user;
        this.customDesign = customDesign;
        this.message = message;
        this.displayStatus = displayStatus != null ? displayStatus : DisplayStatus.WAITING;
    }

    public void markAsDisplayed() {
        this.displayStatus = DisplayStatus.DISPLAYED;
    }
}
