package com.herstory.backend.domain.mypage.dto;

import com.herstory.backend.domain.mypage.MentoringApplication;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "멘토링 프로그램 신청 정보 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class MentoringResponse {

    @Schema(description = "멘토링 신청 ID", example = "1")
    private Long id;

    @Schema(description = "신청 아티스트 ID", example = "1")
    private Long artistId;

    @Schema(description = "프로그램 명칭", example = "AI 패션 패턴 상업화 1:1 멘토링 3기")
    private String programName;

    @Schema(description = "희망 멘토링 주제", example = "전통 문양 스케치의 생성형 AI 학습 및 로열티 정산 구조 관련 피드백 요청")
    private String topic;

    @Schema(description = "신청 상태 (PENDING, APPROVED, COMPLETED, REJECTED)", example = "APPROVED")
    private String status;

    @Schema(description = "신청 일시", example = "2026-08-01T00:00:00")
    private LocalDateTime createdAt;

    public static MentoringResponse from(MentoringApplication application) {
        return MentoringResponse.builder()
                .id(application.getId())
                .artistId(application.getArtist().getId())
                .programName(application.getProgramName())
                .topic(application.getTopic())
                .status(application.getStatus())
                .createdAt(application.getCreatedAt())
                .build();
    }
}
