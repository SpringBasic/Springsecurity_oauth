package com.springsecurity_oauth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class User {
    @Id
    @Column(name = "usr_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "social_type")
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "usr_role")
    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String oauthId;

    private String username;

    private String password;


    @Builder
    private User(Long id, SocialType socialType, RoleType role,
                String oauthId, String username, String password, String email) {
        this.id = id;
        this.socialType = socialType;
        this.role = role;
        this.oauthId = oauthId;
        this.username = username;
        this.password = password;
    }
}
