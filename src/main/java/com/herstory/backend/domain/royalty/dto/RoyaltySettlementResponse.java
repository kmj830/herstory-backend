package com.herstory.backend.domain.royalty.dto;

import com.herstory.backend.domain.royalty.RoyaltySettlement;
import com.herstory.backend.domain.royalty.SettlementStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "로열티 정산 현황 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class RoyaltySettlementResponse {

    @Schema(description = "정산 신청 ID", example = "1")
    private Long id;

    @Schema(description = "아티스트 사용자 ID", example = "1")
    private Long artistId;

    @Schema(description = "누적 판매 매출 총액", example = "4650000")
    private BigDecimal totalSalesAmount;

    @Schema(description = "로열티 배분 비율 (0.15 = 15%)", example = "0.15")
    private Double royaltyRate;

    @Schema(description = "최종 정산 및 출금 지급액", example = "697500")
    private BigDecimal settlementAmount;

    @Schema(description = "정산 처리 상태 (PENDING, COMPLETED, WITHDRAWN)", example = "COMPLETED")
    private SettlementStatus status;

    @Schema(description = "정산 완료 일시", example = "2026-07-31T23:05:00")
    private LocalDateTime settledAt;

    @Schema(description = "정산 신청 일시", example = "2026-07-31T23:00:00")
    private LocalDateTime createdAt;

    public static RoyaltySettlementResponse from(RoyaltySettlement settlement) {
        return RoyaltySettlementResponse.builder()
                .id(settlement.getId())
                .artistId(settlement.getArtist().getId())
                .totalSalesAmount(settlement.getTotalSalesAmount())
                .royaltyRate(settlement.getRoyaltyRate())
                .settlementAmount(settlement.getSettlementAmount())
                .status(settlement.getStatus())
                .settledAt(settlement.getSettledAt())
                .createdAt(settlement.getCreatedAt())
                .build();
    }
}
