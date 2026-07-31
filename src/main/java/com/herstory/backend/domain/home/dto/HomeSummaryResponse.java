package com.herstory.backend.domain.home.dto;

import com.herstory.backend.domain.showroom.dto.ShowroomItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "홈 메인 통합 화면 큐레이션/트렌드 전체 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class HomeSummaryResponse {

    @Schema(description = "HOME-01 브랜드 스토리 안내 정보")
    private BrandStoryResponse brandStory;

    @Schema(description = "HOME-02 실시간 인기 AI 커스텀 패션 아이템 랭킹 Top 5")
    private List<ShowroomItemResponse> popularItems;

    @Schema(description = "HOME-03 이달의 추천 아티스트 피드 배너 목록")
    private List<FeaturedArtistResponse> featuredArtists;

    @Schema(description = "HOME-04 실시간 아티스트 후원금/로열티 누적 카운팅 위젯")
    private SponsorshipStatusResponse sponsorshipStatus;
}
