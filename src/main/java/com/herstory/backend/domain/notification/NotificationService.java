package com.herstory.backend.domain.notification;

import com.herstory.backend.domain.notification.dto.NotificationResponse;
import com.herstory.backend.domain.notification.dto.NotificationSummaryResponse;
import com.herstory.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationSummaryResponse getUserNotifications(Long userId) {
        List<NotificationResponse> notifications = notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId).stream()
                .map(NotificationResponse::from)
                .toList();

        long unreadCount = notificationRepository.countByRecipientIdAndIsReadFalse(userId);

        return NotificationSummaryResponse.builder()
                .unreadCount(unreadCount)
                .notifications(notifications)
                .build();
    }

    @Transactional
    public void markAsRead(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("알림을 찾을 수 없습니다: " + notificationId));

        if (!notification.getRecipient().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 알림만 읽음 처리할 수 있습니다.");
        }

        notification.read();
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> notifications = notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
        notifications.forEach(Notification::read);
    }

    @Transactional
    public NotificationResponse sendNotification(User recipient, String title, String content, NotificationType type, String relatedUrl) {
        Notification notification = Notification.builder()
                .recipient(recipient)
                .title(title)
                .content(content)
                .notificationType(type)
                .relatedUrl(relatedUrl)
                .isRead(false)
                .build();

        return NotificationResponse.from(notificationRepository.save(notification));
    }
}
