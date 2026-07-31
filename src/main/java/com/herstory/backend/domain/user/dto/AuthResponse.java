package com.herstory.backend.domain.user.dto;

import com.herstory.backend.domain.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "로그인 및 회원가입 인증 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {

    @Schema(description = "JWT 접근 토큰 (Bearer 방식)", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String accessToken;

    @Schema(description = "토큰 타입", example = "Bearer")
    private String tokenType;

    @Schema(description = "사용자 PK ID", example = "1")
    private Long userId;

    @Schema(description = "이메일 주소", example = "artist@herstory.com")
    private String email;

    @Schema(description = "사용자 이름", example = "김지민")
    private String name;

    @Schema(description = "사용자 권한 (ROLE_ARTIST: 아티스트, ROLE_CUSTOMER: 고객)", example = "ROLE_ARTIST")
    private Role role;
}
