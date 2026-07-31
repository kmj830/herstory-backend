package com.herstory.backend.domain.mypage.dto;

import com.herstory.backend.domain.user.dto.UserProfileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "MY-01~MY-08 통합 마이페이지 종합 대시보드 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class MyPageSummaryResponse {

    @Schema(description = "MY-07 사용자 프로필 정보")
    private UserProfileResponse userProfile;

    @Schema(description = "MY-01~03 아티스트 대시보드 (아티스트 계정 시 전달)")
    private ArtistDashboardResponse artistDashboard;

    @Schema(description = "MY-04~06 고객 대시보드 (고객 계정 시 전달)")
    private CustomerDashboardResponse customerDashboard;

    @Schema(description = "MY-08 나의 1:1 Q&A 문의 목록")
    private List<QnaInquiryResponse> qnaInquiries;
}
