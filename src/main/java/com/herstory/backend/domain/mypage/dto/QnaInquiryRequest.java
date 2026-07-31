package com.herstory.backend.domain.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "고객센터 1:1 Q&A 문의 등록 요청 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QnaInquiryRequest {

    @Schema(description = "문의 카테고리 (GENERAL, ORDER, MENTORING, ROYALTY)", example = "ORDER")
    private String category;

    @Schema(description = "문의 제목", example = "3D 프린팅 수령 시간 변경 문의드립니다.")
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @Schema(description = "문의 상세 내용", example = "성수 팝업스토어 방문 예약 시간을 15시에서 17시로 변경 가능할까요?")
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
}
