package com.herstory.backend.domain.showroom;

import com.herstory.backend.domain.showroom.dto.*;
import com.herstory.backend.global.common.ApiResponse;
import com.herstory.backend.global.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "4. 버추얼 3D 쇼룸 API (Showroom)", description = "3D 의류 상품 등록, 3D 피팅 감상, 검색/필터링 및 1:1 맞춤 커스터마이징")
@RestController
@RequestMapping("/api/v1/showroom")
@RequiredArgsConstructor
public class ShowroomController {

    private final ShowroomService showroomService;

    @Operation(summary = "3D 쇼룸 상품 등록 (아티스트)", description = "확정된 AI 패턴을 기반으로 버추얼 3D 쇼룸 판매 상품을 등록합니다.")
    @PostMapping("/items")
    public ResponseEntity<ApiResponse<ShowroomItemResponse>> registerItem(
            @Valid @RequestBody ShowroomItemRegisterRequest request) {
        ShowroomItemResponse response = showroomService.registerItem(request);
        return ResponseEntity.ok(ApiResponse.success("3D 쇼룸 아이템 등록 성공", response));
    }

    @Operation(summary = "전체 3D 쇼룸 상품 목록 조회", description = "3D 버추얼 쇼룸에 등록된 모든 의류 상품 목록을 조회합니다.")
    @GetMapping("/items")
    public ResponseEntity<ApiResponse<List<ShowroomItemResponse>>> getAllItems() {
        List<ShowroomItemResponse> items = showroomService.getAllItems();
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @Operation(summary = "3D 쇼룸 상품 키워드 검색 및 가격대/정렬 필터링", description = "키워드, 최소/최대 가격 및 정렬 조건(popular, priceAsc, priceDesc, latest)으로 상품을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ShowroomItemResponse>>> searchItems(
            @Parameter(description = "검색 키워드 (상품명, 설명, 패턴명)", example = "단청") @RequestParam(required = false) String keyword,
            @Parameter(description = "최소 가격 필터 (KRW)", example = "100000") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "최대 가격 필터 (KRW)", example = "300000") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "정렬 조건 (popular: 인기순/후원금순, priceAsc: 낮은가격순, priceDesc: 높은가격순, latest: 최신순)", example = "popular") @RequestParam(required = false, defaultValue = "latest") String sortBy) {
        List<ShowroomItemResponse> items = showroomService.searchItems(keyword, minPrice, maxPrice, sortBy);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @Operation(summary = "3D 쇼룸 상품 상세 조회", description = "3D 모델 렌더링 URL 및 상세 정보를 포함한 상품 정보를 조회합니다.")
    @GetMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<ShowroomItemResponse>> getItem(@PathVariable Long itemId) {
        ShowroomItemResponse item = showroomService.getItem(itemId);
        return ResponseEntity.ok(ApiResponse.success(item));
    }

    @Operation(summary = "1:1 맞춤 커스텀 디자인 생성 (고객)", description = "의류 컬러, 핏, 패턴 위치(JSON)를 실시간 커스텀하고 커스텀 디자인 ID를 생성합니다.")
    @PostMapping("/custom")
    public ResponseEntity<ApiResponse<CustomDesignResponse>> createCustomDesign(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody CustomDesignCreateRequest request) {
        CustomDesignResponse response = showroomService.createCustomDesign(userPrincipal.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("1:1 맞춤 커스텀 디자인 생성 성공", response));
    }
}
