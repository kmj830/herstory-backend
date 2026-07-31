package com.herstory.backend.domain.home;

import com.herstory.backend.domain.home.dto.*;
import com.herstory.backend.domain.showroom.dto.ShowroomItemResponse;
import com.herstory.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "0. 홈 화면 API (Home)", description = "브랜드 스토리, 실시간 인기 아이템 랭킹, 이달의 아티스트 큐레이션 및 실시간 후원 현황 위젯 (HOME-01 ~ HOME-04)")
@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @Operation(summary = "홈 메인 화면 통합 데이터 조회 (HOME-01 ~ HOME-04)", description = "브랜드 스토리, 인기 패션 아이템 Top 5 랭킹, 추천 아티스트 배너 피드 및 누적 후원/로열티 위젯을 한 번에 조회합니다.")
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<HomeSummaryResponse>> getHomeSummary() {
        HomeSummaryResponse summary = homeService.getHomeSummary();
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @Operation(summary = "HOME-01 브랜드 스토리 조회", description = "HER-STORY의 미션, 디자인 미학 및 아티스트 상생 모델 안내 데이터를 조회합니다.")
    @GetMapping("/brand-story")
    public ResponseEntity<ApiResponse<BrandStoryResponse>> getBrandStory() {
        BrandStoryResponse brandStory = homeService.getBrandStory();
        return ResponseEntity.ok(ApiResponse.success(brandStory));
    }

    @Operation(summary = "HOME-02 실시간 인기 AI 커스텀 패션 아이템 랭킹 조회", description = "누적 후원금 및 인기 순으로 정렬된 Top 5 3D 커스텀 패션 아이템 목록을 조회합니다.")
    @GetMapping("/popular-items")
    public ResponseEntity<ApiResponse<List<ShowroomItemResponse>>> getPopularItems() {
        List<ShowroomItemResponse> items = homeService.getPopularItems();
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @Operation(summary = "HOME-03 이달의 추천 아티스트 및 작품 큐레이션 피드 조회", description = "이달의 추천 아티스트 프로필 및 대표 원화 정보 배너 피드를 조회합니다.")
    @GetMapping("/featured-artists")
    public ResponseEntity<ApiResponse<List<FeaturedArtistResponse>>> getFeaturedArtists() {
        List<FeaturedArtistResponse> artists = homeService.getFeaturedArtists();
        return ResponseEntity.ok(ApiResponse.success(artists));
    }

    @Operation(summary = "HOME-04 실시간 아티스트 후원금/로열티 현황 위젯 조회", description = "플랫폼 누적 후원금 총액, 누적 후원자 수 및 정산 완료된 로열티 금액을 실시간 조회합니다.")
    @GetMapping("/sponsorship-status")
    public ResponseEntity<ApiResponse<SponsorshipStatusResponse>> getSponsorshipStatus() {
        SponsorshipStatusResponse status = homeService.getSponsorshipStatus();
        return ResponseEntity.ok(ApiResponse.success(status));
    }
}
