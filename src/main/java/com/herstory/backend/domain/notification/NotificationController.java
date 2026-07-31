package com.herstory.backend.domain.notification;

import com.herstory.backend.domain.notification.dto.NotificationSummaryResponse;
import com.herstory.backend.global.common.ApiResponse;
import com.herstory.backend.global.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "9. 알림센터 API (Notification)", description = "온라인 실시간 후원 수령, 주문 완료, 정산 및 NFT 발급 인앱 알림 관리")
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "나의 온라인 인앱 알림 목록 조회", description = "로그인한 사용자의 안읽은 알림 수 및 알림 리스트를 최신순으로 조회합니다.")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<NotificationSummaryResponse>> getMyNotifications(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        NotificationSummaryResponse response = notificationService.getUserNotifications(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "특정 알림 읽음 처리", description = "단건 알림 ID를 받아 읽음(isRead = true) 상태로 변경합니다.")
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long notificationId) {
        notificationService.markAsRead(userPrincipal.getId(), notificationId);
        return ResponseEntity.ok(ApiResponse.success("알림 읽음 처리 완료", null));
    }

    @Operation(summary = "전체 알림 모두 읽음 처리", description = "로그인한 사용자의 모든 알림을 읽음(isRead = true) 상태로 처리합니다.")
    @PatchMapping("/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        notificationService.markAllAsRead(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success("모든 알림 읽음 처리 완료", null));
    }
}
