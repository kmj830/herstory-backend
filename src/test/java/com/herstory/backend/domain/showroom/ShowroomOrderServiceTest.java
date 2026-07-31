package com.herstory.backend.domain.showroom;

import com.herstory.backend.domain.showroom.dto.*;
import com.herstory.backend.domain.studio.AiPattern;
import com.herstory.backend.domain.studio.AiPatternRepository;
import com.herstory.backend.domain.studio.Artwork;
import com.herstory.backend.domain.studio.ArtworkRepository;
import com.herstory.backend.domain.user.Role;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ShowroomOrderServiceTest {

    @Autowired
    private ShowroomService showroomService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private AiPatternRepository aiPatternRepository;

    @Autowired
    private ShowroomItemRepository showroomItemRepository;

    private User artist;
    private User customer;
    private AiPattern aiPattern;
    private ShowroomItem showroomItem;

    @BeforeEach
    void setUp() {
        artist = userRepository.save(User.builder()
                .email("test-artist-showroom@herstory.com")
                .password("password")
                .name("Artist")
                .role(Role.ROLE_ARTIST)
                .build());

        customer = userRepository.save(User.builder()
                .email("test-customer-showroom@herstory.com")
                .password("password")
                .name("Customer")
                .role(Role.ROLE_CUSTOMER)
                .build());

        Artwork artwork = artworkRepository.save(Artwork.builder()
                .artist(artist)
                .title("Sketch")
                .imageUrl("/uploads/artworks/sketch.png")
                .build());

        aiPattern = aiPatternRepository.save(AiPattern.builder()
                .artwork(artwork)
                .patternName("Pattern 1")
                .patternImageUrl("/uploads/patterns/p1.png")
                .build());

        showroomItem = showroomItemRepository.save(ShowroomItem.builder()
                .aiPattern(aiPattern)
                .title("Custom Jacket")
                .price(new BigDecimal("150000"))
                .rendering3dUrl("https://cdn.herstory.com/3d/jacket.gltf")
                .build());
    }

    @Test
    @DisplayName("커스텀 옵션(컬러, 핏, 패턴 배치 JSON) 저장 테스트")
    void createCustomDesignTest() {
        CustomDesignCreateRequest request = CustomDesignCreateRequest.builder()
                .showroomItemId(showroomItem.getId())
                .customColor("#FF0055")
                .fit("OVERSIZED")
                .patternPlacement("{\"x\": 10, \"y\": 20, \"scale\": 1.5, \"rotation\": 45}")
                .build();

        CustomDesignResponse response = showroomService.createCustomDesign(customer.getId(), request);

        assertThat(response).isNotNull();
        assertThat(response.getCustomColor()).isEqualTo("#FF0055");
        assertThat(response.getFit()).isEqualTo("OVERSIZED");
        assertThat(response.getPatternPlacement()).contains("rotation");
    }

    @Test
    @DisplayName("아티스트 추가 후원금 옵션이 포함된 주문 및 총액 계산 테스트")
    void createOrderWithSponsorshipTest() {
        CustomDesignCreateRequest customReq = CustomDesignCreateRequest.builder()
                .showroomItemId(showroomItem.getId())
                .customColor("#00FF55")
                .fit("REGULAR")
                .patternPlacement("{\"x\": 0, \"y\": 0}")
                .build();
        CustomDesignResponse customRes = showroomService.createCustomDesign(customer.getId(), customReq);

        BigDecimal sponsorshipAmount = new BigDecimal("30000"); // 30,000 KRW sponsorship
        OrderCreateRequest orderRequest = OrderCreateRequest.builder()
                .showroomItemId(showroomItem.getId())
                .customDesignId(customRes.getId())
                .sponsorshipAmount(sponsorshipAmount)
                .shippingAddress("Seoul Gangnam-gu 123")
                .build();

        OrderResponse orderResponse = orderService.createOrder(customer.getId(), orderRequest);

        assertThat(orderResponse).isNotNull();
        assertThat(orderResponse.getSponsorshipAmount()).isEqualTo(sponsorshipAmount);
        assertThat(orderResponse.getAmount()).isEqualTo(new BigDecimal("180000"));

        ShowroomItem updatedItem = showroomItemRepository.findById(showroomItem.getId()).orElseThrow();
        assertThat(updatedItem.getSponsorCount()).isEqualTo(1L);
        assertThat(updatedItem.getTotalSponsorshipAmount()).isEqualTo(sponsorshipAmount);
    }
}
