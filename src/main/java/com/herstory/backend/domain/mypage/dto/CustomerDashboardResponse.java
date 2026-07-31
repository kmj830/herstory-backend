package com.herstory.backend.domain.mypage.dto;

import com.herstory.backend.domain.royalty.dto.NftCertificateResponse;
import com.herstory.backend.domain.showroom.dto.OrderResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "MY-04~MY-06 고객 전용 마이페이지 대시보드 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class CustomerDashboardResponse {

    @Schema(description = "MY-04 커스텀 구매 및 배송 내역 목록")
    private List<OrderResponse> myOrders;

    @Schema(description = "MY-05 디지털 후원 보증서(NFT) 지갑 목록")
    private List<NftCertificateResponse> nftWallet;

    @Schema(description = "MY-06 찜한 상품 및 아티스트 위시리스트 목록")
    private List<WishlistResponse> wishlist;
}
