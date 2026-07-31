package com.herstory.backend.domain.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "온라인 인앱 알림 종합 정보 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class NotificationSummaryResponse {

    @Schema(description = "읽지 않은 안읽은 알림 수", example = "3")
    private long unreadCount;

    @Schema(description = "알림 목록 리스트")
    private List<NotificationResponse> notifications;
}
