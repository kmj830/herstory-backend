package com.herstory.backend.domain.user.dto;

import com.herstory.backend.domain.user.OAuth2Provider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "OAuth2 소셜 인가(Authorization) URL 안내 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class OAuth2AuthorizeUrlResponse {

    @Schema(description = "소셜 제공자 (KAKAO, GOOGLE)", example = "KAKAO")
    private OAuth2Provider provider;

    @Schema(description = "프론트엔드가 리다이렉트할 인가 코드 요청 URL", example = "https://kauth.kakao.com/oauth/authorize?client_id=HERSTORY_CLIENT_ID&redirect_uri=http://localhost:3000/oauth/callback/kakao&response_type=code")
    private String authorizationUrl;
}
