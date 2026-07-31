package com.herstory.backend.domain.mypage.dto;

import com.herstory.backend.domain.mypage.Wishlist;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "위시리스트 정보 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class WishlistResponse {

    @Schema(description = "위시리스트 PK ID", example = "1")
    private Long id;

    @Schema(description = "사용자 ID", example = "2")
    private Long userId;

    @Schema(description = "찜한 3D 쇼룸 상품 ID", example = "1")
    private Long showroomItemId;

    @Schema(description = "찜한 상품 제목", example = "3D Custom Neon Dancheong Silk Jacket")
    private String showroomItemTitle;

    @Schema(description = "찜한 아티스트 ID", example = "1")
    private Long artistId;

    @Schema(description = "찜한 아티스트 이름", example = "김지민")
    private String artistName;

    @Schema(description = "등록 일시", example = "2026-08-01T00:00:00")
    private LocalDateTime createdAt;

    public static WishlistResponse from(Wishlist item) {
        return WishlistResponse.builder()
                .id(item.getId())
                .userId(item.getUser().getId())
                .showroomItemId(item.getShowroomItem() != null ? item.getShowroomItem().getId() : null)
                .showroomItemTitle(item.getShowroomItem() != null ? item.getShowroomItem().getTitle() : null)
                .artistId(item.getArtist() != null ? item.getArtist().getId() : null)
                .artistName(item.getArtist() != null ? item.getArtist().getName() : null)
                .createdAt(item.getCreatedAt())
                .build();
    }
}
