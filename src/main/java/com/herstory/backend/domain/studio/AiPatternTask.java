package com.herstory.backend.domain.studio;

import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ai_pattern_tasks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiPatternTask extends BaseTimeEntity {

    @Id
    private String taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id", nullable = false)
    private Artwork artwork;

    @Column(nullable = false)
    private String patternName;

    private String prompt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PatternTaskStatus status;

    private String resultImageUrl;

    private String errorMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_pattern_id")
    private AiPattern generatedPattern;

    @Builder
    public AiPatternTask(String taskId, Artwork artwork, String patternName, String prompt, PatternTaskStatus status) {
        this.taskId = taskId;
        this.artwork = artwork;
        this.patternName = patternName;
        this.prompt = prompt;
        this.status = status != null ? status : PatternTaskStatus.PENDING;
    }

    public void updateStatus(PatternTaskStatus status) {
        this.status = status;
    }

    public void markAsCompleted(String resultImageUrl, AiPattern generatedPattern) {
        this.status = PatternTaskStatus.COMPLETED;
        this.resultImageUrl = resultImageUrl;
        this.generatedPattern = generatedPattern;
    }

    public void markAsFailed(String errorMessage) {
        this.status = PatternTaskStatus.FAILED;
        this.errorMessage = errorMessage;
    }
}
