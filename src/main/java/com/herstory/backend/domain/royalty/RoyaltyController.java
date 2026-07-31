package com.herstory.backend.domain.royalty;

import com.herstory.backend.domain.royalty.dto.*;
import com.herstory.backend.global.common.ApiResponse;
import com.herstory.backend.global.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "6. 로열티 & 디지털 보증서 API (Royalty)", description = "아티스트 정산 현황/출금 신청 및 디지털 후원 보증서(NFT)")
@RestController
@RequestMapping("/api/v1/royalty")
@RequiredArgsConstructor
public class RoyaltyController {

    private final RoyaltyService royaltyService;

    @Operation(summary = "나의 로열티 정산 내역 조회 (아티스트)", description = "아티스트 본인의 누적 매출 정산 현황 및 정산 상태를 조회합니다.")
    @GetMapping("/settlements/my")
    public ResponseEntity<ApiResponse<List<RoyaltySettlementResponse>>> getMySettlements(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<RoyaltySettlementResponse> settlements = royaltyService.getArtistSettlements(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(settlements));
    }

    @Operation(summary = "로열티 정산 신청 (아티스트)", description = "발생한 매출 금액에 따른 로열티 정산 및 출금을 신청합니다.")
    @PostMapping("/settlements")
    public ResponseEntity<ApiResponse<RoyaltySettlementResponse>> createSettlement(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam BigDecimal totalSalesAmount) {
        RoyaltySettlementResponse response = royaltyService.createSettlementRequest(userPrincipal.getId(), totalSalesAmount);
        return ResponseEntity.ok(ApiResponse.success("로열티 정산 신청 완료", response));
    }

    @Operation(summary = "디지털 후원 보증서(NFT) 발급", description = "후원금이 포함된 구매 주문 건에 대해 블록체인 기반 디지털 보증서(NFT 메타데이터)를 발급합니다.")
    @PostMapping("/certificates/issue")
    public ResponseEntity<ApiResponse<NftCertificateResponse>> issueCertificate(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody NftCertificateIssueRequest request) {
        NftCertificateResponse response = royaltyService.issueNftCertificate(userPrincipal.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("디지털 후원 보증서 발급 완료", response));
    }

    @Operation(summary = "나의 디지털 보증서 지갑(Wallet) 조회 (고객)", description = "고객이 발급받은 정품 인증 및 후원 증명 디지털 보증서(NFT) 지갑 목록을 조회합니다.")
    @GetMapping("/certificates/my")
    public ResponseEntity<ApiResponse<List<NftCertificateResponse>>> getMyCertificates(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<NftCertificateResponse> certificates = royaltyService.getCustomerCertificates(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(certificates));
    }
}
