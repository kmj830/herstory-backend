package com.herstory.backend.domain.o2o.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "현장 디지털 캔버스 Wall 전송 요청 DTO")
@Getter
@NoArgsConstructor
public class MediaWallPostSendRequest {

    @Schema(description = "전시할 1:1 맞춤 커스텀 디자인 ID", example = "1")
    private Long customDesignId;

    @Schema(description = "미디어 월 표출 메시지", example = "성수 팝업스토어 미디어 월에 나의 3D 단청 재킷 커스텀이 전시 중입니다! #HERSTORY")
    private String message;
}
