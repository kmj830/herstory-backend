package com.herstory.backend.domain.showroom.dto;

import com.herstory.backend.domain.showroom.ShowroomItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "3D 쇼룸 상품 상세 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class ShowroomItemResponse {

    @Schema(description = "쇼룸 상품 ID", example = "1")
    private Long id;

    @Schema(description = "적용된 AI 패턴 ID", example = "1")
    private Long aiPatternId;

    @Schema(description = "상품 제목", example = "3D Custom Neon Dancheong Silk Jacket")
    private String title;

    @Schema(description = "판매 가격 (KRW)", example = "289000")
    private BigDecimal price;

    @Schema(description = "상품 상세 설명", example = "3D 버추얼 피팅 실시간 커스텀 실크 재킷")
    private String description;

    @Schema(description = "3D 입체 피팅 모델 URL (.gltf)", example = "https://cdn.herstory.com/3d/models/dancheong_jacket.gltf")
    private String rendering3dUrl;

    @Schema(description = "누적 아티스트 후원자 수", example = "42")
    private Long sponsorCount;

    @Schema(description = "누적 아티스트 후원금 총액 (KRW)", example = "1260000")
    private BigDecimal totalSponsorshipAmount;

    @Schema(description = "상품 등록 일시", example = "2026-07-31T23:05:00")
    private LocalDateTime createdAt;

    public static ShowroomItemResponse from(ShowroomItem item) {
        return ShowroomItemResponse.builder()
                .id(item.getId())
                .aiPatternId(item.getAiPattern().getId())
                .title(item.getTitle())
                .price(item.getPrice())
                .description(item.getDescription())
                .rendering3dUrl(item.getRendering3dUrl())
                .sponsorCount(item.getSponsorCount())
                .totalSponsorshipAmount(item.getTotalSponsorshipAmount())
                .createdAt(item.getCreatedAt())
                .build();
    }
}
