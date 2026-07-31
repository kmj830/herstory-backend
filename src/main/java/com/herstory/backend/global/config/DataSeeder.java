package com.herstory.backend.global.config;

import com.herstory.backend.domain.o2o.*;
import com.herstory.backend.domain.royalty.*;
import com.herstory.backend.domain.showroom.*;
import com.herstory.backend.domain.studio.*;
import com.herstory.backend.domain.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ArtworkRepository artworkRepository;
    private final AiPatternRepository aiPatternRepository;
    private final ShowroomItemRepository showroomItemRepository;
    private final CustomDesignRepository customDesignRepository;
    private final OrderRepository orderRepository;
    private final RoyaltySettlementRepository royaltySettlementRepository;
    private final NftCertificateRepository nftCertificateRepository;
    private final MediaWallPostRepository mediaWallPostRepository;
    private final PrintReservationRepository printReservationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("artist@herstory.com").isPresent()) {
            log.info("[DataSeeder] Data already exists. Skipping initial data seeding.");
            return;
        }

        log.info("[DataSeeder] Seeding initial hackathon demo data...");

        // 1. Create Users
        User artist = userRepository.save(User.builder()
                .email("artist@herstory.com")
                .password(passwordEncoder.encode("password123"))
                .name("김지민 (Jimin Kim)")
                .role(Role.ROLE_ARTIST)
                .bio("Generative AI와 한국 전통 문양을 결합하여 현대적 패션 패턴을 창작하는 아티스트")
                .profileImageUrl("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400")
                .build());

        User customer = userRepository.save(User.builder()
                .email("customer@herstory.com")
                .password(passwordEncoder.encode("password123"))
                .name("이소연 (Soyeon Lee)")
                .role(Role.ROLE_CUSTOMER)
                .bio("지속가능한 커스텀 패션과 아티스트 후원에 관심이 많은 에코 소비 트렌드 세터")
                .profileImageUrl("https://images.unsplash.com/photo-1517841905240-472988babdf9?w=400")
                .build());

        log.info("[DataSeeder] Sample users created: artist@herstory.com, customer@herstory.com");

        // 2. Create Artworks (STUDIO-01)
        Artwork artwork1 = artworkRepository.save(Artwork.builder()
                .artist(artist)
                .title("단청과 빛 (Dancheong & Light)")
                .description("한국 전통 단청의 강렬한 조형미와 색채 파동을 재해석한 원화 드로잉")
                .imageUrl("https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=800")
                .build());

        Artwork artwork2 = artworkRepository.save(Artwork.builder()
                .artist(artist)
                .title("수묵의 구름 (Ink Cloud Flow)")
                .description("동양적 수묵 잔향과 현대적 그라데이션의 오버랩 스케치")
                .imageUrl("https://images.unsplash.com/photo-1541701494587-cb58502866ab?w=800")
                .build());

        Artwork artwork3 = artworkRepository.save(Artwork.builder()
                .artist(artist)
                .title("조각보의 파동 (Patchwork Wave)")
                .description("조상들의 지혜가 담긴 조각보 패턴의 색채 재구성")
                .imageUrl("https://images.unsplash.com/photo-1607604276583-eef5d076aa5f?w=800")
                .build());

        // 3. Create AI Patterns (STUDIO-02)
        AiPattern pattern1 = aiPatternRepository.save(AiPattern.builder()
                .artwork(artwork1)
                .patternName("Neon Dancheong Cyber Pattern")
                .prompt("cyberpunk neon dancheong Korean traditional pattern high quality 8k fashion textile")
                .patternImageUrl("https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=800")
                .build());

        AiPattern pattern2 = aiPatternRepository.save(AiPattern.builder()
                .artwork(artwork2)
                .patternName("Silk Ink Ripple Pattern")
                .prompt("oriental ink flow pattern on luxurious silk fabric soft gradient")
                .patternImageUrl("https://images.unsplash.com/photo-1550684848-fac1c5b4e853?w=800")
                .build());

        AiPattern pattern3 = aiPatternRepository.save(AiPattern.builder()
                .artwork(artwork3)
                .patternName("Modern Patchwork Geometry")
                .prompt("sustainable modern geometric patchwork textile pattern soft pastel colors")
                .patternImageUrl("https://images.unsplash.com/photo-1579783902614-a3fb3927b675?w=800")
                .build());

        log.info("[DataSeeder] Sample Artworks and AI Patterns created.");

        // 4. Create 3D Showroom Items (SHOW-01, STUDIO-03)
        ShowroomItem item1 = showroomItemRepository.save(ShowroomItem.builder()
                .aiPattern(pattern1)
                .title("3D Custom Neon Dancheong Silk Jacket")
                .price(new BigDecimal("289000"))
                .description("3D 버추얼 피팅 실시간 커스텀 실크 재킷. 아티스트 김지민의 대표 단청 패턴 적용.")
                .rendering3dUrl("https://cdn.herstory.com/3d/models/dancheong_jacket.gltf")
                .build());
        item1.addSponsorship(new BigDecimal("1260000"));

        ShowroomItem item2 = showroomItemRepository.save(ShowroomItem.builder()
                .aiPattern(pattern2)
                .title("Eco-Friendly 3D Printed Ink Pattern Hoodie")
                .price(new BigDecimal("149000"))
                .description("친환경 리사이클 소재 기반 3D 입체 커스텀 후디. 은은한 수묵 먹의 흐름 패턴.")
                .rendering3dUrl("https://cdn.herstory.com/3d/models/ink_hoodie.gltf")
                .build());
        item2.addSponsorship(new BigDecimal("2640000"));

        ShowroomItem item3 = showroomItemRepository.save(ShowroomItem.builder()
                .aiPattern(pattern3)
                .title("Virtual Patchwork Overcoat")
                .price(new BigDecimal("340000"))
                .description("ESG 탄소 발자국 절감 3D 오버핏 코트. 조각보 패턴의 현대적 조화.")
                .rendering3dUrl("https://cdn.herstory.com/3d/models/patchwork_coat.gltf")
                .build());
        item3.addSponsorship(new BigDecimal("750000"));

        log.info("[DataSeeder] 3 Showroom items created.");

        // 5. Create Custom Design & Order (SHOW-02, SHOW-03, SHOW-04)
        CustomDesign customDesign = customDesignRepository.save(CustomDesign.builder()
                .customer(customer)
                .showroomItem(item1)
                .customColor("#FF0055")
                .fit("OVERSIZED")
                .patternPlacement("{\"x\": 15, \"y\": 25, \"scale\": 1.4, \"rotation\": 30}")
                .build());

        BigDecimal sponsorshipAmount = new BigDecimal("50000");
        Order order = orderRepository.save(Order.builder()
                .customer(customer)
                .showroomItem(item1)
                .customDesign(customDesign)
                .amount(item1.getPrice().add(sponsorshipAmount))
                .sponsorshipAmount(sponsorshipAmount)
                .shippingAddress("서울특별시 성동구 아차산로 111 팝업빌딩 402호")
                .status(OrderStatus.PAID)
                .build());

        // 6. Create Royalty Settlement & NFT Certificate (IMPACT-01, IMPACT-02)
        royaltySettlementRepository.save(RoyaltySettlement.builder()
                .artist(artist)
                .totalSalesAmount(new BigDecimal("4650000"))
                .royaltyRate(0.15)
                .settlementAmount(new BigDecimal("697500"))
                .status(SettlementStatus.COMPLETED)
                .build());

        nftCertificateRepository.save(NftCertificate.builder()
                .customer(customer)
                .order(order)
                .tokenId("HERSTORY-NFT-SEONGSU-001")
                .metadataUri("https://api.herstory.com/nft/metadata/HERSTORY-NFT-SEONGSU-001")
                .contractAddress("0x71C7656EC7ab88b098defB751B7401B5f6d8976F")
                .issuedAt(LocalDateTime.now())
                .build());

        // 7. Create O2O Media Wall Post & 3D Print Reservation (O2O-01, O2O-02, O2O-03)
        mediaWallPostRepository.save(MediaWallPost.builder()
                .user(customer)
                .customDesign(customDesign)
                .message("성수 팝업스토어 미디어 월에 나의 3D 단청 재킷 커스텀이 전시 중입니다! #HERSTORY #3D패션")
                .displayStatus(DisplayStatus.DISPLAYED)
                .build());

        printReservationRepository.save(PrintReservation.builder()
                .user(customer)
                .showroomItem(item2)
                .reservationTime(LocalDateTime.now().plusHours(3))
                .status(ReservationStatus.RESERVED)
                .build());

        log.info("[DataSeeder] Initial hackathon demo data seeding completed successfully!");
    }
}
