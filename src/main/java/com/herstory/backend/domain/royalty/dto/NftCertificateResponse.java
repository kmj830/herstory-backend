package com.herstory.backend.domain.royalty.dto;

import com.herstory.backend.domain.royalty.NftCertificate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "디지털 후원 보증서(NFT) 정보 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class NftCertificateResponse {

    @Schema(description = "보증서 ID", example = "1")
    private Long id;

    @Schema(description = "발급 고객 ID", example = "2")
    private Long customerId;

    @Schema(description = "연관 주문 ID", example = "1")
    private Long orderId;

    @Schema(description = "NFT 토큰 ID", example = "HERSTORY-NFT-SEONGSU-001")
    private String tokenId;

    @Schema(description = "블록체인 메타데이터 URI", example = "https://api.herstory.com/nft/metadata/HERSTORY-NFT-SEONGSU-001")
    private String metadataUri;

    @Schema(description = "스마트 컨트랙트 주소", example = "0x71C7656EC7ab88b098defB751B7401B5f6d8976F")
    private String contractAddress;

    @Schema(description = "발급 일시", example = "2026-07-31T23:05:00")
    private LocalDateTime issuedAt;

    public static NftCertificateResponse from(NftCertificate cert) {
        return NftCertificateResponse.builder()
                .id(cert.getId())
                .customerId(cert.getCustomer().getId())
                .orderId(cert.getOrder().getId())
                .tokenId(cert.getTokenId())
                .metadataUri(cert.getMetadataUri())
                .contractAddress(cert.getContractAddress())
                .issuedAt(cert.getIssuedAt())
                .build();
    }
}
