package com.herstory.backend.domain.royalty;

import com.herstory.backend.domain.user.User;
import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "royalty_settlements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoyaltySettlement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private User artist;

    @Column(nullable = false)
    private BigDecimal totalSalesAmount;

    @Column(nullable = false)
    private Double royaltyRate;

    @Column(nullable = false)
    private BigDecimal settlementAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SettlementStatus status;

    private LocalDateTime settledAt;

    @Builder
    public RoyaltySettlement(User artist, BigDecimal totalSalesAmount, Double royaltyRate, BigDecimal settlementAmount, SettlementStatus status) {
        this.artist = artist;
        this.totalSalesAmount = totalSalesAmount;
        this.royaltyRate = royaltyRate != null ? royaltyRate : 0.15; // default 15%
        this.settlementAmount = settlementAmount;
        this.status = status != null ? status : SettlementStatus.PENDING;
    }

    public void completeSettlement() {
        this.status = SettlementStatus.COMPLETED;
        this.settledAt = LocalDateTime.now();
    }

    public void withdraw() {
        this.status = SettlementStatus.WITHDRAWN;
    }
}
