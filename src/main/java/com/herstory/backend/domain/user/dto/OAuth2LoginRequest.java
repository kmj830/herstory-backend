package com.herstory.backend.domain.user.dto;

import com.herstory.backend.domain.user.OAuth2Provider;
import com.herstory.backend.domain.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "OAuth2 소셜 로그인/회원가입 요청 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2LoginRequest {

    @Schema(description = "소셜 로그인 제공자 (KAKAO, GOOGLE)", example = "KAKAO")
    @NotNull(message = "소셜 로그인 제공자는 필수입니다.")
    private OAuth2Provider provider;

    @Schema(description = "소셜 인가 코드(Authorization Code) 또는 액세스 토큰", example = "kakao_auth_code_12345")
    private String code;

    @Schema(description = "소셜 사용자 이메일 (클라이언트 직접 전달 가능)", example = "user_kakao@herstory.com")
    private String email;

    @Schema(description = "소셜 사용자 이름 (클라이언트 직접 전달 가능)", example = "카카오사용자")
    private String name;

    @Schema(description = "프로필 이미지 URL (선택)", example = "https://k.kakaocdn.net/dn/profile.jpg")
    private String profileImageUrl;

    @Schema(description = "회원가입 시 부여할 권한 (ROLE_CUSTOMER, ROLE_ARTIST)", example = "ROLE_CUSTOMER")
    private Role role;
}
