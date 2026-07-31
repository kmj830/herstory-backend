package com.herstory.backend.domain.royalty.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "디지털 후원 보증서(NFT) 발급 요청 DTO")
@Getter
@NoArgsConstructor
public class NftCertificateIssueRequest {

    @Schema(description = "보증서를 발급할 결제 완료 주문 ID", example = "1")
    @NotNull(message = "주문 ID는 필수입니다.")
    private Long orderId;
}
