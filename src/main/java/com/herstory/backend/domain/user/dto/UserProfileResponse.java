package com.herstory.backend.domain.user.dto;

import com.herstory.backend.domain.user.Role;
import com.herstory.backend.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 프로필 정보 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponse {

    @Schema(description = "사용자 ID", example = "1")
    private Long id;

    @Schema(description = "이메일 주소", example = "artist@herstory.com")
    private String email;

    @Schema(description = "사용자 이름", example = "김지민")
    private String name;

    @Schema(description = "사용자 권한", example = "ROLE_ARTIST")
    private Role role;

    @Schema(description = "자기소개 및 바이오", example = "전통 단청과 AI 패턴을 결합하는 패션 아티스트입니다.")
    private String bio;

    @Schema(description = "프로필 이미지 URL", example = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400")
    private String profileImageUrl;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .bio(user.getBio())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
