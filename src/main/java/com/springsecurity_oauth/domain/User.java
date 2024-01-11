package com.springsecurity_oauth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
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

    private String email;

}
