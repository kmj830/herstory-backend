package com.herstory.backend.domain.user;

import com.herstory.backend.domain.user.dto.*;
import com.herstory.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "1. 인증 API (Auth)", description = "회원가입, 일반/소셜(Kakao/Google) 로그인 토큰 발급 관리")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final OAuth2Service oauth2Service;

    @Operation(summary = "회원가입", description = "아티스트 또는 고객 신규 회원가입을 처리하고 JWT 접근 토큰을 발급합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        AuthResponse response = userService.signUp(request);
        return ResponseEntity.ok(ApiResponse.success("회원가입 성공", response));
    }

    @Operation(summary = "일반 로그인", description = "이메일과 비밀번호로 로그인 후 JWT 접근 토큰을 발급합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(ApiResponse.success("로그인 성공", response));
    }

    @Operation(summary = "OAuth2 소셜 로그인 인가 URL 안내 (Kakao / Google)", description = "프론트엔드가 소셜 로그인을 위해 이동시킬 인가 코드(Authorization Code) URL을 안내받습니다.")
    @GetMapping("/oauth2/authorize-url/{provider}")
    public ResponseEntity<ApiResponse<OAuth2AuthorizeUrlResponse>> getAuthorizeUrl(@PathVariable OAuth2Provider provider) {
        OAuth2AuthorizeUrlResponse response = oauth2Service.getAuthorizeUrl(provider);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "OAuth2 소셜 간편 로그인 / 자동 회원가입", description = "카카오(Kakao) 또는 구글(Google) 소셜 인가 코드로 간편 회원가입 및 JWT 토큰을 발급받습니다.")
    @PostMapping("/oauth2/login")
    public ResponseEntity<ApiResponse<AuthResponse>> oauth2Login(@Valid @RequestBody OAuth2LoginRequest request) {
        AuthResponse response = oauth2Service.processOAuth2Login(request);
        return ResponseEntity.ok(ApiResponse.success("소셜 로그인 성공", response));
    }
}
