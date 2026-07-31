package com.herstory.backend.domain.studio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "AI 패턴 생성 비동기 요청 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatternGenerateRequest {

    @Schema(description = "기반 원화 ID", example = "1")
    @NotNull(message = "원화 ID는 필수입니다.")
    private Long artworkId;

    @Schema(description = "생성할 패턴 이름", example = "Neon Dancheong Cyber Pattern")
    @NotBlank(message = "패턴 이름은 필수입니다.")
    private String patternName;

    @Schema(description = "AI 생성 텍스트 프롬프트", example = "cyberpunk neon dancheong Korean traditional pattern high quality 8k fashion textile")
    private String prompt;
}
