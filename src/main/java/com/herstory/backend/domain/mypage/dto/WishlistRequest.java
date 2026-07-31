package com.herstory.backend.domain.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "위시리스트 등록/해제 요청 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistRequest {

    @Schema(description = "찜할 3D 쇼룸 상품 ID (선택)", example = "1")
    private Long showroomItemId;

    @Schema(description = "찜할 아티스트 사용자 ID (선택)", example = "1")
    private Long artistId;
}
