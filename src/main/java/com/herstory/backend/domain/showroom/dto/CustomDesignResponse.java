package com.herstory.backend.domain.showroom.dto;

import com.herstory.backend.domain.showroom.CustomDesign;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "1:1 맞춤 커스텀 디자인 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class CustomDesignResponse {

    @Schema(description = "커스텀 디자인 ID", example = "1")
    private Long id;

    @Schema(description = "고객 사용자 ID", example = "2")
    private Long customerId;

    @Schema(description = "쇼룸 상품 ID", example = "1")
    private Long showroomItemId;

    @Schema(description = "커스텀 메인 컬러 Hex", example = "#FF0055")
    private String customColor;

    @Schema(description = "핏 스타일", example = "OVERSIZED")
    private String fit;

    @Schema(description = "패턴 3D 배치/회전 정보 (JSON)", example = "{\"x\": 15, \"y\": 25, \"scale\": 1.4, \"rotation\": 30}")
    private String patternPlacement;

    @Schema(description = "생성 일시", example = "2026-07-31T23:05:00")
    private LocalDateTime createdAt;

    public static CustomDesignResponse from(CustomDesign design) {
        return CustomDesignResponse.builder()
                .id(design.getId())
                .customerId(design.getCustomer().getId())
                .showroomItemId(design.getShowroomItem().getId())
                .customColor(design.getCustomColor())
                .fit(design.getFit())
                .patternPlacement(design.getPatternPlacement())
                .createdAt(design.getCreatedAt())
                .build();
    }
}
