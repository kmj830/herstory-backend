package com.herstory.backend.domain.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "HOME-03 이달의 추천 아티스트 피드 배너 DTO")
@Getter
@Builder
@AllArgsConstructor
public class FeaturedArtistResponse {

    @Schema(description = "아티스트 사용자 ID", example = "1")
    private Long artistId;

    @Schema(description = "아티스트 이름", example = "김지민 (Jimin Kim)")
    private String artistName;

    @Schema(description = "프로필 이미지 URL", example = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400")
    private String profileImageUrl;

    @Schema(description = "아티스트 바이오/소개", example = "Generative AI와 한국 전통 문양을 결합하여 현대적 패션 패턴을 창작하는 아티스트")
    private String bio;

    @Schema(description = "등록 원화 작품 수", example = "3")
    private int artworkCount;

    @Schema(description = "대표 원화 제목", example = "단청과 빛 (Dancheong & Light)")
    private String representativeArtworkTitle;

    @Schema(description = "대표 원화 이미지 URL", example = "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=800")
    private String representativeArtworkUrl;
}
