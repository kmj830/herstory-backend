package com.herstory.backend.domain.studio.dto;

import com.herstory.backend.domain.studio.Artwork;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "원화/스케치 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class ArtworkResponse {

    @Schema(description = "원화 ID", example = "1")
    private Long id;

    @Schema(description = "아티스트 사용자 ID", example = "1")
    private Long artistId;

    @Schema(description = "아티스트 이름", example = "김지민")
    private String artistName;

    @Schema(description = "원화 제목", example = "단청과 빛 (Dancheong & Light)")
    private String title;

    @Schema(description = "원화 설명", example = "한국 전통 단청의 강렬한 조형미와 색채 파동을 재해석한 원화 드로잉")
    private String description;

    @Schema(description = "원화 이미지 URL", example = "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=800")
    private String imageUrl;

    @Schema(description = "등록 일시", example = "2026-07-31T23:00:00")
    private LocalDateTime createdAt;

    public static ArtworkResponse from(Artwork artwork) {
        return ArtworkResponse.builder()
                .id(artwork.getId())
                .artistId(artwork.getArtist().getId())
                .artistName(artwork.getArtist().getName())
                .title(artwork.getTitle())
                .description(artwork.getDescription())
                .imageUrl(artwork.getImageUrl())
                .createdAt(artwork.getCreatedAt())
                .build();
    }
}
