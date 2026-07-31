package com.herstory.backend.domain.mypage.dto;

import com.herstory.backend.domain.mypage.QnaInquiry;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "고객센터 1:1 Q&A 문의 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class QnaInquiryResponse {

    @Schema(description = "문의 PK ID", example = "1")
    private Long id;

    @Schema(description = "등록 사용자 ID", example = "2")
    private Long userId;

    @Schema(description = "문의 카테고리", example = "ORDER")
    private String category;

    @Schema(description = "문의 제목", example = "3D 프린팅 수령 시간 변경 문의드립니다.")
    private String title;

    @Schema(description = "문의 내용", example = "성수 팝업스토어 방문 예약 시간을 15시에서 17시로 변경 가능할까요?")
    private String content;

    @Schema(description = "답변 내용", example = "안녕하세요! 현장 상황에 따라 변경 가능합니다. 방문 시 인포데스크에 말씀해 주세요.")
    private String answer;

    @Schema(description = "답변 완료 여부", example = "true")
    private boolean isAnswered;

    @Schema(description = "문의 등록 일시", example = "2026-08-01T00:00:00")
    private LocalDateTime createdAt;

    public static QnaInquiryResponse from(QnaInquiry inquiry) {
        return QnaInquiryResponse.builder()
                .id(inquiry.getId())
                .userId(inquiry.getUser().getId())
                .category(inquiry.getCategory())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .answer(inquiry.getAnswer())
                .isAnswered(inquiry.isAnswered())
                .createdAt(inquiry.getCreatedAt())
                .build();
    }
}
