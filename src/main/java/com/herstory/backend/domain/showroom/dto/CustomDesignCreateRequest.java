package com.herstory.backend.domain.showroom.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "1:1 맞춤 커스텀 디자인 생성 요청 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomDesignCreateRequest {

    @Schema(description = "대상 3D 쇼룸 상품 ID", example = "1")
    @NotNull(message = "쇼룸 상품 ID는 필수입니다.")
    private Long showroomItemId;

    @Schema(description = "커스텀 의류 컬러 (Hex 코드)", example = "#FF0055")
    private String customColor;

    @Schema(description = "핏 스타일 (REGULAR, OVERSIZED, SLIM)", example = "OVERSIZED")
    private String fit;

    @Schema(description = "패턴 3D 배치/회전 정보 (JSON)", example = "{\"x\": 15, \"y\": 25, \"scale\": 1.4, \"rotation\": 30}")
    private String patternPlacement;
}
