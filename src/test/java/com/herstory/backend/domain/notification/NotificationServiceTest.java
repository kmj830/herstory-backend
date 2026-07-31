package com.herstory.backend.domain.notification;

import com.herstory.backend.domain.notification.dto.NotificationResponse;
import com.herstory.backend.domain.notification.dto.NotificationSummaryResponse;
import com.herstory.backend.domain.user.Role;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    private User artist;

    @BeforeEach
    void setUp() {
        artist = userRepository.save(User.builder()
                .email("noti-artist@herstory.com")
                .password("password123")
                .name("Artist Noti")
                .role(Role.ROLE_ARTIST)
                .build());
    }

    @Test
    @DisplayName("알림 발송, 안읽은 알림 수 계산 및 읽음 처리 테스트")
    void notificationFlowTest() {
        NotificationResponse noti1 = notificationService.sendNotification(
                artist,
                "후원금 수령 알림",
                "아티스트님의 패턴 작품에 50,000원의 후원금이 수령되었습니다.",
                NotificationType.SPONSORSHIP_RECEIVED,
                "/mypage/artist-dashboard"
        );

        NotificationResponse noti2 = notificationService.sendNotification(
                artist,
                "정산 완료 알림",
                "로열티 정산이 성공적으로 진행되었습니다.",
                NotificationType.ROYALTY_SETTLED,
                "/mypage/artist-dashboard"
        );

        NotificationSummaryResponse summaryBefore = notificationService.getUserNotifications(artist.getId());
        assertThat(summaryBefore.getUnreadCount()).isEqualTo(2);

        notificationService.markAsRead(artist.getId(), noti1.getId());

        NotificationSummaryResponse summaryAfter = notificationService.getUserNotifications(artist.getId());
        assertThat(summaryAfter.getUnreadCount()).isEqualTo(1);

        notificationService.markAllAsRead(artist.getId());
        NotificationSummaryResponse summaryFinal = notificationService.getUserNotifications(artist.getId());
        assertThat(summaryFinal.getUnreadCount()).isEqualTo(0);
    }
}
