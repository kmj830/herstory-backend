package com.herstory.backend.domain.mypage;

import com.herstory.backend.domain.mypage.dto.*;
import com.herstory.backend.domain.user.dto.UserProfileResponse;
import com.herstory.backend.global.common.ApiResponse;
import com.herstory.backend.global.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "8. 마이페이지 API (MyPage)", description = "아티스트 작품/정산/멘토링 관리, 고객 구매/NFT지갑/위시리스트 및 계정/고객센터 관리 (MY-01 ~ MY-08)")
@RestController
@RequestMapping("/api/v1/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "마이페이지 통합 대시보드 조회 (MY-01 ~ MY-08)", description = "로그인한 사용자의 프로필, 역할별 대시보드(아티스트/고객), 위시리스트 및 Q&A 목록을 한 번에 조회합니다.")
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<MyPageSummaryResponse>> getMyPageSummary(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        MyPageSummaryResponse response = myPageService.getMyPageSummary(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "MY-01~03 아티스트 대시보드 조회", description = "아티스트의 나의 작품/패턴, 로열티 정산/출금 내역 및 멘토링 신청 현황을 조회합니다.")
    @GetMapping("/artist-dashboard")
    public ResponseEntity<ApiResponse<ArtistDashboardResponse>> getArtistDashboard(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ArtistDashboardResponse response = myPageService.getArtistDashboard(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "MY-04~06 고객 대시보드 조회", description = "고객의 구매/배송 내역, 디지털 후원 보증서(NFT) 지갑 및 위시리스트 목록을 조회합니다.")
    @GetMapping("/customer-dashboard")
    public ResponseEntity<ApiResponse<CustomerDashboardResponse>> getCustomerDashboard(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        CustomerDashboardResponse response = myPageService.getCustomerDashboard(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "MY-06 위시리스트 추가", description = "관심 있는 3D 쇼룸 상품 또는 아티스트를 찜 목록에 추가합니다.")
    @PostMapping("/wishlist")
    public ResponseEntity<ApiResponse<WishlistResponse>> addWishlist(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody WishlistRequest request) {
        WishlistResponse response = myPageService.addWishlist(userPrincipal.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("위시리스트 등록 완료", response));
    }

    @Operation(summary = "MY-06 위시리스트 삭제", description = "위시리스트 항목을 삭제합니다.")
    @DeleteMapping("/wishlist/{wishlistId}")
    public ResponseEntity<ApiResponse<Void>> deleteWishlist(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long wishlistId) {
        myPageService.deleteWishlist(userPrincipal.getId(), wishlistId);
        return ResponseEntity.ok(ApiResponse.success("위시리스트 삭제 완료", null));
    }

    @Operation(summary = "MY-08 고객센터 1:1 Q&A 문의 등록", description = "고객센터 1:1 문의를 등록합니다.")
    @PostMapping("/qna")
    public ResponseEntity<ApiResponse<QnaInquiryResponse>> createQnaInquiry(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody QnaInquiryRequest request) {
        QnaInquiryResponse response = myPageService.createQnaInquiry(userPrincipal.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("1:1 문의 등록 완료", response));
    }

    @Operation(summary = "MY-03 아티스트 멘토링 프로그램 신청", description = "아티스트 멘토링 프로그램 및 1:1 피드백 신청을 접수합니다.")
    @PostMapping("/mentoring")
    public ResponseEntity<ApiResponse<MentoringResponse>> applyMentoring(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody MentoringRequest request) {
        MentoringResponse response = myPageService.applyMentoring(userPrincipal.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("멘토링 프로그램 신청 완료", response));
    }

    @Operation(summary = "MY-07 계정 프로필 수정", description = "사용자의 이름, 자기소개 및 프로필 이미지 URL을 수정합니다.")
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String bio,
            @RequestParam(required = false) String profileImageUrl) {
        UserProfileResponse response = myPageService.updateProfile(userPrincipal.getId(), name, bio, profileImageUrl);
        return ResponseEntity.ok(ApiResponse.success("프로필 수정 완료", response));
    }
}
