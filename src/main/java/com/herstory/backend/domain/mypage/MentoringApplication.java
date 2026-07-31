package com.herstory.backend.domain.mypage;

import com.herstory.backend.domain.user.User;
import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mentoring_applications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentoringApplication extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private User artist;

    @Column(nullable = false)
    private String programName;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private String status; // PENDING, APPROVED, COMPLETED, REJECTED

    @Builder
    public MentoringApplication(User artist, String programName, String topic, String status) {
        this.artist = artist;
        this.programName = programName;
        this.topic = topic;
        this.status = status != null ? status : "PENDING";
    }

    public void updateStatus(String status) {
        this.status = status;
    }
}
