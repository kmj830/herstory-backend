package com.herstory.backend.domain.user;

import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String bio;

    private String profileImageUrl;

    @Builder
    public User(String email, String password, String name, Role role, String bio, String profileImageUrl) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role != null ? role : Role.ROLE_CUSTOMER;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
    }

    public void updateProfile(String name, String bio, String profileImageUrl) {
        if (name != null) this.name = name;
        if (bio != null) this.bio = bio;
        if (profileImageUrl != null) this.profileImageUrl = profileImageUrl;
    }
}
