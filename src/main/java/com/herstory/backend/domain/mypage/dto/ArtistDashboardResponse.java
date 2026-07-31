package com.herstory.backend.domain.mypage.dto;

import com.herstory.backend.domain.royalty.dto.RoyaltySettlementResponse;
import com.herstory.backend.domain.studio.dto.ArtworkResponse;
import com.herstory.backend.domain.studio.dto.PatternResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "MY-01~MY-03 아티스트 전용 마이페이지 대시보드 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class ArtistDashboardResponse {

    @Schema(description = "MY-01 등록 원화 작품 목록")
    private List<ArtworkResponse> myArtworks;

    @Schema(description = "MY-01 생성된 AI 패턴 목록")
    private List<PatternResponse> myPatterns;

    @Schema(description = "MY-02 총 판매 매출액", example = "4650000")
    private BigDecimal totalSalesAmount;

    @Schema(description = "MY-02 누적 로열티 정산액", example = "697500")
    private BigDecimal totalRoyaltyAmount;

    @Schema(description = "MY-02 출금 가능 잔액", example = "697500")
    private BigDecimal withdrawableAmount;

    @Schema(description = "MY-02 로열티 정산 및 출금 내역")
    private List<RoyaltySettlementResponse> settlementHistory;

    @Schema(description = "MY-03 멘토링 프로그램 신청 및 Q&A 내역")
    private List<MentoringResponse> mentoringApplications;
}
