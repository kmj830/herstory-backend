package com.herstory.backend.domain.notification;

import com.herstory.backend.domain.user.User;
import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    private String relatedUrl;

    @Column(nullable = false)
    private boolean isRead;

    @Builder
    public Notification(User recipient, String title, String content, NotificationType notificationType, String relatedUrl, Boolean isRead) {
        this.recipient = recipient;
        this.title = title;
        this.content = content;
        this.notificationType = notificationType != null ? notificationType : NotificationType.SYSTEM;
        this.relatedUrl = relatedUrl;
        this.isRead = isRead != null ? isRead : false;
    }

    public void read() {
        this.isRead = true;
    }
}
