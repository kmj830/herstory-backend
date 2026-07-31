package com.herstory.backend.domain.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "아티스트 멘토링 프로그램 신청 요청 DTO")
@Getter
@NoArgsConstructor
public class MentoringRequest {

    @Schema(description = "신청할 멘토링 프로그램 명칭", example = "AI 패션 패턴 상업화 1:1 멘토링 3기")
    @NotBlank(message = "프로그램 명칭은 필수입니다.")
    private String programName;

    @Schema(description = "희망 멘토링 주제 및 문의 사항", example = "전통 문양 스케치의 생성형 AI 학습 및 로열티 정산 구조 관련 피드백 요청")
    @NotBlank(message = "주제는 필수입니다.")
    private String topic;
}
