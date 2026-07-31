package com.herstory.backend.domain.user;

import com.herstory.backend.domain.user.dto.AuthResponse;
import com.herstory.backend.domain.user.dto.OAuth2AuthorizeUrlResponse;
import com.herstory.backend.domain.user.dto.OAuth2LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OAuth2ServiceTest {

    @Autowired
    private OAuth2Service oauth2Service;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("소셜 로그인 인가 URL 안내 테스트")
    void getAuthorizeUrlTest() {
        OAuth2AuthorizeUrlResponse response = oauth2Service.getAuthorizeUrl(OAuth2Provider.KAKAO);

        assertThat(response).isNotNull();
        assertThat(response.getProvider()).isEqualTo(OAuth2Provider.KAKAO);
        assertThat(response.getAuthorizationUrl()).contains("kauth.kakao.com");
    }

    @Test
    @DisplayName("카카오 소셜 간편로그인 및 자동 회원가입 테스트")
    void processOAuth2LoginKakaoTest() {
        OAuth2LoginRequest request = OAuth2LoginRequest.builder()
                .provider(OAuth2Provider.KAKAO)
                .email("kakao_test_user@herstory.com")
                .name("카카오테스트")
                .role(Role.ROLE_CUSTOMER)
                .build();

        AuthResponse response = oauth2Service.processOAuth2Login(request);

        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isNotBlank();
        assertThat(response.getEmail()).isEqualTo("kakao_test_user@herstory.com");
        assertThat(userRepository.findByEmail("kakao_test_user@herstory.com")).isPresent();
    }
}
