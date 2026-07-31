package com.herstory.backend.domain.studio.dto;

import com.herstory.backend.domain.studio.AiPatternTask;
import com.herstory.backend.domain.studio.PatternTaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "AI 패턴 생성 비동기 작업 상태 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class PatternTaskResponse {

    @Schema(description = "비동기 작업 고유 ID", example = "TASK-A1B2C3D4")
    private String taskId;

    @Schema(description = "기반 원화 ID", example = "1")
    private Long artworkId;

    @Schema(description = "생성할 패턴 이름", example = "Neon Dancheong Cyber Pattern")
    private String patternName;

    @Schema(description = "작업 처리 상태 (PENDING, IN_PROGRESS, COMPLETED, FAILED)", example = "COMPLETED")
    private PatternTaskStatus status;

    @Schema(description = "생성 완료된 패턴 이미지 URL", example = "/uploads/patterns/pattern_12345.png")
    private String resultImageUrl;

    @Schema(description = "실패 시 에러 메시지", example = "null")
    private String errorMessage;

    @Schema(description = "생성 완료된 AI 패턴 PK ID", example = "1")
    private Long generatedPatternId;

    @Schema(description = "작업 생성 일시", example = "2026-07-31T23:05:00")
    private LocalDateTime createdAt;

    public static PatternTaskResponse from(AiPatternTask task) {
        return PatternTaskResponse.builder()
                .taskId(task.getTaskId())
                .artworkId(task.getArtwork().getId())
                .patternName(task.getPatternName())
                .status(task.getStatus())
                .resultImageUrl(task.getResultImageUrl())
                .errorMessage(task.getErrorMessage())
                .generatedPatternId(task.getGeneratedPattern() != null ? task.getGeneratedPattern().getId() : null)
                .createdAt(task.getCreatedAt())
                .build();
    }
}
