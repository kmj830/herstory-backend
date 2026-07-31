package com.herstory.backend.global.config;

import com.herstory.backend.domain.o2o.MediaWallPostRepository;
import com.herstory.backend.domain.o2o.PrintReservationRepository;
import com.herstory.backend.domain.royalty.NftCertificateRepository;
import com.herstory.backend.domain.royalty.RoyaltySettlementRepository;
import com.herstory.backend.domain.showroom.OrderRepository;
import com.herstory.backend.domain.showroom.ShowroomItemRepository;
import com.herstory.backend.domain.studio.AiPatternRepository;
import com.herstory.backend.domain.studio.ArtworkRepository;
import com.herstory.backend.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DataSeederTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private AiPatternRepository aiPatternRepository;

    @Autowired
    private ShowroomItemRepository showroomItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RoyaltySettlementRepository royaltySettlementRepository;

    @Autowired
    private NftCertificateRepository nftCertificateRepository;

    @Autowired
    private MediaWallPostRepository mediaWallPostRepository;

    @Autowired
    private PrintReservationRepository printReservationRepository;

    @Test
    @DisplayName("애플리케이션 구동 시 해커톤 시연용 샘플 데이터 자동 주입 테스트")
    void testInitialDataSeeding() {
        assertThat(userRepository.findByEmail("artist@herstory.com")).isPresent();
        assertThat(userRepository.findByEmail("customer@herstory.com")).isPresent();
        assertThat(artworkRepository.findAll()).hasSizeGreaterThanOrEqualTo(3);
        assertThat(aiPatternRepository.findAll()).hasSizeGreaterThanOrEqualTo(3);
        assertThat(showroomItemRepository.findAll()).hasSizeGreaterThanOrEqualTo(3);
        assertThat(orderRepository.findAll()).isNotEmpty();
        assertThat(royaltySettlementRepository.findAll()).isNotEmpty();
        assertThat(nftCertificateRepository.findAll()).isNotEmpty();
        assertThat(mediaWallPostRepository.findAll()).isNotEmpty();
        assertThat(printReservationRepository.findAll()).isNotEmpty();
    }
}
