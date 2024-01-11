package com.springsecurity_oauth.domain.oauth;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

/* 카카오 소셜 로그인 */
public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    private final OAuth2User oAuth2User;
    public KakaoOAuth2UserInfo(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(oAuth2User, clientRegistration);
        this.oAuth2User = oAuth2User;
    }

    @Override
    public String getId() {
        return oAuth2User.getName();
    }

    @Override
    public String getUsername() {
        return (String) getProfile().get("nickname");
    }
    private Map<String, Object> getProfile() { return (Map<String, Object>) getKakaoAccount().get("profile");}
    private Map<String, Object> getKakaoAccount() {
        return (Map<String, Object>) getAttributes().get("kakao_account");
    }
}
