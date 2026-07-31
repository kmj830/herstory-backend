package com.herstory.backend.domain.showroom.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "주문 및 후원 결제 요청 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {

    @Schema(description = "쇼룸 상품 ID", example = "1")
    @NotNull(message = "쇼룸 상품 ID는 필수입니다.")
    private Long showroomItemId;

    @Schema(description = "1:1 맞춤 커스텀 디자인 ID (선택)", example = "1")
    private Long customDesignId;

    @Schema(description = "아티스트 추가 후원금 (KRW)", example = "50000")
    private BigDecimal sponsorshipAmount;

    @Schema(description = "배송지 주소", example = "서울특별시 성동구 아차산로 111 팝업빌딩 402호")
    @NotNull(message = "배송지는 필수입니다.")
    private String shippingAddress;
}
