package com.herstory.backend.domain.showroom;

import com.herstory.backend.domain.user.User;
import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showroom_item_id", nullable = false)
    private ShowroomItem showroomItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_design_id")
    private CustomDesign customDesign;

    @Column(nullable = false)
    private BigDecimal amount;

    private BigDecimal sponsorshipAmount;

    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Builder
    public Order(User customer, ShowroomItem showroomItem, CustomDesign customDesign, BigDecimal amount, BigDecimal sponsorshipAmount, String shippingAddress, OrderStatus status) {
        this.customer = customer;
        this.showroomItem = showroomItem;
        this.customDesign = customDesign;
        this.amount = amount;
        this.sponsorshipAmount = sponsorshipAmount != null ? sponsorshipAmount : BigDecimal.ZERO;
        this.shippingAddress = shippingAddress;
        this.status = status != null ? status : OrderStatus.PENDING;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
}
