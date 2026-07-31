package com.herstory.backend.domain.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Schema(description = "HOME-04 실시간 아티스트 후원/로열티 현황 위젯 DTO")
@Getter
@Builder
@AllArgsConstructor
public class SponsorshipStatusResponse {

    @Schema(description = "누적 아티스트 후원금 총액 (KRW)", example = "4650000")
    private BigDecimal totalSponsorshipAmount;

    @Schema(description = "누적 후원자 수", example = "155")
    private Long totalSponsorCount;

    @Schema(description = "누적 지급 완료 로열티 정산액 (KRW)", example = "697500")
    private BigDecimal totalRoyaltySettled;

    @Schema(description = "후원 혜택을 받은 누적 아티스트 수", example = "1")
    private int supportedArtistCount;
}
