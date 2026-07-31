package com.herstory.backend.domain.showroom.dto;

import com.herstory.backend.domain.showroom.Order;
import com.herstory.backend.domain.showroom.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "주문 및 후원 결제 결과 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class OrderResponse {

    @Schema(description = "주문 ID", example = "1")
    private Long id;

    @Schema(description = "주문 고객 ID", example = "2")
    private Long customerId;

    @Schema(description = "주문 상품 ID", example = "1")
    private Long showroomItemId;

    @Schema(description = "적용된 커스텀 디자인 ID", example = "1")
    private Long customDesignId;

    @Schema(description = "총 결제 금액 (상품가 + 후원금)", example = "339000")
    private BigDecimal amount;

    @Schema(description = "아티스트 추가 후원금", example = "50000")
    private BigDecimal sponsorshipAmount;

    @Schema(description = "배송지 주소", example = "서울특별시 성동구 아차산로 111")
    private String shippingAddress;

    @Schema(description = "주문 상태 (PENDING, PAID, CANCELLED, SHIPPED, DELIVERED)", example = "PAID")
    private OrderStatus status;

    @Schema(description = "주문 일시", example = "2026-07-31T23:05:00")
    private LocalDateTime createdAt;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .showroomItemId(order.getShowroomItem().getId())
                .customDesignId(order.getCustomDesign() != null ? order.getCustomDesign().getId() : null)
                .amount(order.getAmount())
                .sponsorshipAmount(order.getSponsorshipAmount())
                .shippingAddress(order.getShippingAddress())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
