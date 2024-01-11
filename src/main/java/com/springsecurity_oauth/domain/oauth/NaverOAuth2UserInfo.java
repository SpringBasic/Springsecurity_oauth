package com.springsecurity_oauth.domain.oauth;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

/* 네이버 소셜 로그인 */
public class NaverOAuth2UserInfo extends OAuth2UserInfo{
    private final OAuth2User oAuth2User;
    public NaverOAuth2UserInfo(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(oAuth2User, clientRegistration);
        this.oAuth2User = oAuth2User;
    }

    @Override
    public String getId() {
        return oAuth2User.getName();
    }

    @Override
    public String getUsername() {
        return (String)((Map<String,Object>)getAttributes().get("response")).get("email");
    }
}
