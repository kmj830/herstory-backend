package com.herstory.backend.domain.mypage;

import com.herstory.backend.domain.mypage.dto.*;
import com.herstory.backend.domain.user.Role;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MyPageServiceTest {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private UserRepository userRepository;

    private User artist;
    private User customer;

    @BeforeEach
    void setUp() {
        artist = userRepository.save(User.builder()
                .email("mypage-artist@herstory.com")
                .password("password123")
                .name("Artist MyPage")
                .role(Role.ROLE_ARTIST)
                .bio("MyPage Artist Bio")
                .build());

        customer = userRepository.save(User.builder()
                .email("mypage-customer@herstory.com")
                .password("password123")
                .name("Customer MyPage")
                .role(Role.ROLE_CUSTOMER)
                .bio("MyPage Customer Bio")
                .build());
    }

    @Test
    @DisplayName("MY-01~08 마이페이지 종합 대시보드 조회 테스트 (아티스트)")
    void getMyPageSummaryArtistTest() {
        MyPageSummaryResponse summary = myPageService.getMyPageSummary(artist.getId());

        assertThat(summary).isNotNull();
        assertThat(summary.getUserProfile()).isNotNull();
        assertThat(summary.getUserProfile().getEmail()).isEqualTo("mypage-artist@herstory.com");
        assertThat(summary.getArtistDashboard()).isNotNull();
    }

    @Test
    @DisplayName("MY-06 위시리스트 등록 및 조회 테스트")
    void addWishlistTest() {
        WishlistRequest request = WishlistRequest.builder()
                .artistId(artist.getId())
                .build();

        WishlistResponse response = myPageService.addWishlist(customer.getId(), request);

        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo(customer.getId());
        assertThat(response.getArtistId()).isEqualTo(artist.getId());
    }

    @Test
    @DisplayName("MY-08 고객센터 1:1 Q&A 문의 등록 테스트")
    void createQnaInquiryTest() {
        QnaInquiryRequest request = QnaInquiryRequest.builder()
                .category("ORDER")
                .title("3D 프린팅 수령 변경 문의")
                .content("성수 팝업스토어 방문 예약 시간을 15시에서 17시로 변경 가능할까요?")
                .build();

        QnaInquiryResponse response = myPageService.createQnaInquiry(customer.getId(), request);

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("3D 프린팅 수령 변경 문의");
        assertThat(response.isAnswered()).isFalse();
    }
}
