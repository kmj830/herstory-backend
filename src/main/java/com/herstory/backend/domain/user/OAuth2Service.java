package com.herstory.backend.domain.user;

import com.herstory.backend.domain.user.dto.AuthResponse;
import com.herstory.backend.domain.user.dto.OAuth2AuthorizeUrlResponse;
import com.herstory.backend.domain.user.dto.OAuth2LoginRequest;
import com.herstory.backend.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuth2Service {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${oauth2.kakao.client-id:e28fd538d6b49c7fb7634bf9f05b0796}")
    private String kakaoClientId;

    @Value("${oauth2.kakao.redirect-uri:http://localhost:3000/oauth/callback/kakao}")
    private String kakaoRedirectUri;

    @Value("${oauth2.google.client-id:706222420739-d2tmjjdu762p8619dev17trfuibjik89.apps.googleusercontent.com}")
    private String googleClientId;

    @Value("${oauth2.google.redirect-uri:http://localhost:3000/oauth/callback/google}")
    private String googleRedirectUri;

    public OAuth2AuthorizeUrlResponse getAuthorizeUrl(OAuth2Provider provider) {
        String url;
        if (provider == OAuth2Provider.KAKAO) {
            url = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                    kakaoClientId, kakaoRedirectUri);
        } else {
            url = String.format("https://accounts.google.com/o/oauth2/v2/auth?client_id=%s&redirect_uri=%s&response_type=code&scope=email%%20profile",
                    googleClientId, googleRedirectUri);
        }

        return OAuth2AuthorizeUrlResponse.builder()
                .provider(provider)
                .authorizationUrl(url)
                .build();
    }

    @Transactional
    public AuthResponse processOAuth2Login(OAuth2LoginRequest request) {
        String email = request.getEmail();
        String name = request.getName();

        if (!StringUtils.hasText(email)) {
            email = request.getProvider().name().toLowerCase() + "_" + UUID.randomUUID().toString().substring(0, 8) + "@herstory.com";
        }

        if (!StringUtils.hasText(name)) {
            name = request.getProvider().name() + " 사용자";
        }

        String finalEmail = email;
        String finalName = name;
        Role finalRole = request.getRole() != null ? request.getRole() : Role.ROLE_CUSTOMER;

        User user = userRepository.findByEmail(finalEmail)
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(finalEmail)
                        .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                        .name(finalName)
                        .role(finalRole)
                        .bio(request.getProvider().name() + " 소셜 로그인으로 가입한 회원입니다.")
                        .profileImageUrl(request.getProfileImageUrl())
                        .build()));

        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
