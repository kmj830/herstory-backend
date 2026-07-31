package com.herstory.backend.domain.studio.dto;

import com.herstory.backend.domain.studio.AiPattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "생성된 AI 패턴 정보 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class PatternResponse {

    @Schema(description = "AI 패턴 ID", example = "1")
    private Long id;

    @Schema(description = "기반 원화 ID", example = "1")
    private Long artworkId;

    @Schema(description = "패턴 이름", example = "Neon Dancheong Cyber Pattern")
    private String patternName;

    @Schema(description = "생성된 패턴 이미지 URL", example = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=800")
    private String patternImageUrl;

    @Schema(description = "사용된 생성 프롬프트", example = "cyberpunk neon dancheong Korean traditional pattern")
    private String prompt;

    @Schema(description = "생성 일시", example = "2026-07-31T23:05:00")
    private LocalDateTime createdAt;

    public static PatternResponse from(AiPattern pattern) {
        return PatternResponse.builder()
                .id(pattern.getId())
                .artworkId(pattern.getArtwork().getId())
                .patternName(pattern.getPatternName())
                .patternImageUrl(pattern.getPatternImageUrl())
                .prompt(pattern.getPrompt())
                .createdAt(pattern.getCreatedAt())
                .build();
    }
}
