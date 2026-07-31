package com.herstory.backend.domain.mypage;

import com.herstory.backend.domain.user.User;
import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qna_inquiries")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaInquiry extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    private String category; // GENERAL, ORDER, MENTORING, ROYALTY

    @Column(length = 2000)
    private String answer;

    private boolean isAnswered;

    @Builder
    public QnaInquiry(User user, String title, String content, String category, String answer, Boolean isAnswered) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.category = category != null ? category : "GENERAL";
        this.answer = answer;
        this.isAnswered = isAnswered != null ? isAnswered : false;
    }

    public void reply(String answer) {
        this.answer = answer;
        this.isAnswered = true;
    }
}
