package com.herstory.backend.domain.user.dto;

import com.herstory.backend.domain.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원가입 요청 DTO")
@Getter
@NoArgsConstructor
public class SignUpRequest {

    @Schema(description = "이메일 주소", example = "artist@herstory.com")
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Schema(description = "비밀번호", example = "password123")
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @Schema(description = "사용자 이름", example = "김지민")
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @Schema(description = "권한 (ROLE_ARTIST: 아티스트, ROLE_CUSTOMER: 고객)", example = "ROLE_ARTIST")
    private Role role;

    @Schema(description = "자기소개 / 프로필 설명", example = "전통 단청과 AI 패턴을 결합하는 패션 아티스트입니다.")
    private String bio;
}
