package com.herstory.backend.domain.showroom.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "3D 쇼룸 판매 상품 등록 요청 DTO")
@Getter
@NoArgsConstructor
public class ShowroomItemRegisterRequest {

    @Schema(description = "적용할 AI 패턴 ID", example = "1")
    @NotNull(message = "AI 패턴 ID는 필수입니다.")
    private Long aiPatternId;

    @Schema(description = "3D 쇼룸 상품 제목", example = "3D Custom Neon Dancheong Silk Jacket")
    @NotBlank(message = "상품 제목은 필수입니다.")
    private String title;

    @Schema(description = "상품 판매 가격 (KRW)", example = "289000")
    @NotNull(message = "가격은 필수입니다.")
    private BigDecimal price;

    @Schema(description = "상품 상세 설명", example = "3D 버추얼 피팅 실시간 커스텀 실크 재킷")
    private String description;

    @Schema(description = "3D 모델 파일 (.gltf / .glb) URL", example = "https://cdn.herstory.com/3d/models/dancheong_jacket.gltf")
    private String rendering3dUrl;
}
