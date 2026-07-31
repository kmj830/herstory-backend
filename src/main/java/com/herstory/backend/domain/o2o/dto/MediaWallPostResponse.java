package com.herstory.backend.domain.o2o.dto;

import com.herstory.backend.domain.o2o.DisplayStatus;
import com.herstory.backend.domain.o2o.MediaWallPost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "미디어 월 게시물 정보 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class MediaWallPostResponse {

    @Schema(description = "게시물 ID", example = "1")
    private Long id;

    @Schema(description = "전송 사용자 ID", example = "2")
    private Long userId;

    @Schema(description = "전송 사용자 이름", example = "이소연")
    private String userName;

    @Schema(description = "연관 커스텀 디자인 ID", example = "1")
    private Long customDesignId;

    @Schema(description = "전시 메시지", example = "성수 팝업스토어 미디어 월에 나의 3D 단청 재킷 커스텀이 전시 중입니다!")
    private String message;

    @Schema(description = "디스플레이 상태 (WAITING, DISPLAYED, REJECTED)", example = "DISPLAYED")
    private DisplayStatus displayStatus;

    @Schema(description = "전송 일시", example = "2026-07-31T23:05:00")
    private LocalDateTime createdAt;

    public static MediaWallPostResponse from(MediaWallPost post) {
        return MediaWallPostResponse.builder()
                .id(post.getId())
                .userId(post.getUser().getId())
                .userName(post.getUser().getName())
                .customDesignId(post.getCustomDesign() != null ? post.getCustomDesign().getId() : null)
                .message(post.getMessage())
                .displayStatus(post.getDisplayStatus())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
