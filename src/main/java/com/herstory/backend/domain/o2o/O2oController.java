package com.herstory.backend.domain.o2o;

import com.herstory.backend.domain.o2o.dto.*;
import com.herstory.backend.global.common.ApiResponse;
import com.herstory.backend.global.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "7. O2O 팝업스토어 API (O2O)", description = "성수 팝업스토어 정보, 현장 디지털 캔버스 Wall 및 3D 프린팅 수령 예약")
@RestController
@RequestMapping("/api/v1/o2o")
@RequiredArgsConstructor
public class O2oController {

    private final O2oService o2oService;

    @Operation(summary = "팝업스토어 안내 및 모니터링", description = "오프라인 팝업스토어 위치, 운영시간, 실시간 3D 프린트 가동 상태를 조회합니다.")
    @GetMapping("/popup-info")
    public ResponseEntity<ApiResponse<PopupStoreInfoResponse>> getPopupInfo() {
        PopupStoreInfoResponse info = o2oService.getPopupStoreInfo();
        return ResponseEntity.ok(ApiResponse.success(info));
    }

    @Operation(summary = "현장 디지털 캔버스 Wall 전송", description = "자신이 디자인한 커스텀 패션과 응원 메시지를 오프라인 미디어 월로 전송합니다.")
    @PostMapping("/media-wall")
    public ResponseEntity<ApiResponse<MediaWallPostResponse>> sendToMediaWall(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody MediaWallPostSendRequest request) {
        MediaWallPostResponse response = o2oService.sendToMediaWall(userPrincipal.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("미디어 월 전송 요청 성공", response));
    }

    @Operation(summary = "친환경 3D 프린팅 현장 수령 예약", description = "오프라인 팝업스토어에서 친환경 3D 프린팅 완성본을 직접 수령할 시간을 예약합니다.")
    @PostMapping("/print-reservations")
    public ResponseEntity<ApiResponse<PrintReservationResponse>> createPrintReservation(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody PrintReservationCreateRequest request) {
        PrintReservationResponse response = o2oService.createPrintReservation(userPrincipal.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("친환경 3D 프린팅 예약 성공", response));
    }

    @Operation(summary = "나의 3D 프린팅 예약 내역 조회", description = "로그인한 고객의 오프라인 팝업스토어 3D 프린팅 수령 예약 목록을 조회합니다.")
    @GetMapping("/print-reservations/my")
    public ResponseEntity<ApiResponse<List<PrintReservationResponse>>> getMyReservations(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<PrintReservationResponse> reservations = o2oService.getUserReservations(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(reservations));
    }
}
