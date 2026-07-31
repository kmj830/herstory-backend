package com.herstory.backend.domain.studio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "원화/스케치 등록 요청 DTO (URL 기반)")
@Getter
@NoArgsConstructor
public class ArtworkUploadRequest {

    @Schema(description = "원화/스케치 제목", example = "단청과 빛 (Dancheong & Light)")
    @NotBlank(message = "작품 제목은 필수입니다.")
    private String title;

    @Schema(description = "원화 상세 설명", example = "한국 전통 단청의 강렬한 조형미와 색채 파동을 재해석한 원화 드로잉")
    private String description;

    @Schema(description = "원화 이미지 URL", example = "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=800")
    @NotBlank(message = "이미지 URL은 필수입니다.")
    private String imageUrl;
}
