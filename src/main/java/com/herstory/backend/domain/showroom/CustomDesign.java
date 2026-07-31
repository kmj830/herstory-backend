package com.herstory.backend.domain.showroom;

import com.herstory.backend.domain.user.User;
import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "custom_designs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomDesign extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showroom_item_id", nullable = false)
    private ShowroomItem showroomItem;

    private String customColor;

    private String fit;

    private String patternPlacement;

    @Builder
    public CustomDesign(User customer, ShowroomItem showroomItem, String customColor, String fit, String patternPlacement) {
        this.customer = customer;
        this.showroomItem = showroomItem;
        this.customColor = customColor;
        this.fit = fit;
        this.patternPlacement = patternPlacement;
    }
}
