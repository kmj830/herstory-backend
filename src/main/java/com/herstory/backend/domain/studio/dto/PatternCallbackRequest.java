package com.herstory.backend.domain.studio.dto;

import com.herstory.backend.domain.studio.PatternTaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "외부 AI 엔진 완료/실패 웹훅 콜백 요청 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatternCallbackRequest {

    @Schema(description = "작업 ID", example = "TASK-A1B2C3D4")
    @NotBlank(message = "작업 ID는 필수입니다.")
    private String taskId;

    @Schema(description = "작업 결과 상태 (COMPLETED, FAILED)", example = "COMPLETED")
    @NotNull(message = "작업 상태는 필수입니다.")
    private PatternTaskStatus status;

    @Schema(description = "생성 완료된 패턴 이미지 URL", example = "https://cdn.herstory.com/patterns/result.png")
    private String patternImageUrl;

    @Schema(description = "실패 사유 메시지", example = "AI Engine Timeout")
    private String errorMessage;
}
