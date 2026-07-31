package com.herstory.backend.domain.royalty;

import com.herstory.backend.domain.showroom.Order;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "nft_certificates")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NftCertificate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, unique = true)
    private String tokenId;

    private String metadataUri;

    private String contractAddress;

    private LocalDateTime issuedAt;

    @Builder
    public NftCertificate(User customer, Order order, String tokenId, String metadataUri, String contractAddress, LocalDateTime issuedAt) {
        this.customer = customer;
        this.order = order;
        this.tokenId = tokenId;
        this.metadataUri = metadataUri;
        this.contractAddress = contractAddress;
        this.issuedAt = issuedAt != null ? issuedAt : LocalDateTime.now();
    }
}
