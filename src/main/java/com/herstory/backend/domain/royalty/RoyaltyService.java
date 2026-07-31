package com.herstory.backend.domain.royalty;

import com.herstory.backend.domain.notification.NotificationService;
import com.herstory.backend.domain.notification.NotificationType;
import com.herstory.backend.domain.royalty.dto.*;
import com.herstory.backend.domain.showroom.Order;
import com.herstory.backend.domain.showroom.OrderRepository;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoyaltyService {

    private final RoyaltySettlementRepository royaltySettlementRepository;
    private final NftCertificateRepository nftCertificateRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public List<RoyaltySettlementResponse> getArtistSettlements(Long artistId) {
        return royaltySettlementRepository.findByArtistId(artistId).stream()
                .map(RoyaltySettlementResponse::from)
                .toList();
    }

    @Transactional
    public RoyaltySettlementResponse createSettlementRequest(Long artistId, BigDecimal totalSalesAmount) {
        User artist = userRepository.findById(artistId)
                .orElseThrow(() -> new IllegalArgumentException("아티스트를 찾을 수 없습니다."));

        Double defaultRate = 0.15;
        BigDecimal settlementAmount = totalSalesAmount.multiply(BigDecimal.valueOf(defaultRate));

        RoyaltySettlement settlement = RoyaltySettlement.builder()
                .artist(artist)
                .totalSalesAmount(totalSalesAmount)
                .royaltyRate(defaultRate)
                .settlementAmount(settlementAmount)
                .status(SettlementStatus.PENDING)
                .build();

        RoyaltySettlement savedSettlement = royaltySettlementRepository.save(settlement);

        notificationService.sendNotification(
                artist,
                "로열티 정산 신청 완료",
                "매출액 " + totalSalesAmount + "원에 대한 정산 신청(정산예정액: " + settlementAmount + "원)이 접수되었습니다.",
                NotificationType.ROYALTY_SETTLED,
                "/mypage/artist-dashboard"
        );

        return RoyaltySettlementResponse.from(savedSettlement);
    }

    @Transactional
    public NftCertificateResponse issueNftCertificate(Long customerId, NftCertificateIssueRequest request) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        if (nftCertificateRepository.findByOrderId(order.getId()).isPresent()) {
            throw new IllegalArgumentException("이미 해당 주문에 대한 디지털 보증서가 발급되었습니다.");
        }

        String tokenId = "HERSTORY-NFT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String metadataUri = "https://api.herstory.com/nft/metadata/" + tokenId;
        String contractAddress = "0x71C7656EC7ab88b098defB751B7401B5f6d8976F";

        NftCertificate cert = NftCertificate.builder()
                .customer(customer)
                .order(order)
                .tokenId(tokenId)
                .metadataUri(metadataUri)
                .contractAddress(contractAddress)
                .build();

        NftCertificate savedCert = nftCertificateRepository.save(cert);

        notificationService.sendNotification(
                customer,
                "디지털 후원 보증서(NFT) 발급 완료!",
                "주문 #" + order.getId() + " 건에 대한 블록체인 정품 및 후원 증명 보증서(" + tokenId + ")가 발급되었습니다.",
                NotificationType.NFT_ISSUED,
                "/mypage/customer-dashboard"
        );

        return NftCertificateResponse.from(savedCert);
    }

    public List<NftCertificateResponse> getCustomerCertificates(Long customerId) {
        return nftCertificateRepository.findByCustomerId(customerId).stream()
                .map(NftCertificateResponse::from)
                .toList();
    }
}
