package com.herstory.backend.domain.notification.dto;

import com.herstory.backend.domain.notification.Notification;
import com.herstory.backend.domain.notification.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "온라인 인앱 알림 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class NotificationResponse {

    @Schema(description = "알림 ID", example = "1")
    private Long id;

    @Schema(description = "수신 사용자 ID", example = "1")
    private Long recipientId;

    @Schema(description = "알림 제목", example = "아티스트 추가 후원금 도착!")
    private String title;

    @Schema(description = "알림 상세 내용", example = "이소연 님이 [3D Custom Neon Dancheong Silk Jacket] 상품에 50,000원의 후원금을 함께 결제했습니다.")
    private String content;

    @Schema(description = "알림 유형 (SPONSORSHIP_RECEIVED, ORDER_PAID, ROYALTY_SETTLED, NFT_ISSUED, SYSTEM)", example = "SPONSORSHIP_RECEIVED")
    private NotificationType notificationType;

    @Schema(description = "이동 관련 URL", example = "/mypage/artist-dashboard")
    private String relatedUrl;

    @Schema(description = "읽음 여부 (true/false)", example = "false")
    private boolean isRead;

    @Schema(description = "알림 발생 일시", example = "2026-08-01T00:20:00")
    private LocalDateTime createdAt;

    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .recipientId(notification.getRecipient().getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .notificationType(notification.getNotificationType())
                .relatedUrl(notification.getRelatedUrl())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
