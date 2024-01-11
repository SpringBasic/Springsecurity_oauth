package com.springsecurity_oauth.domain.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * - 모든 소셜 로그인 타입이 가지는 공통 필드 (id,username 제외)
 * - OAuth2UserService loadUser 의 결과로 받은 OAuthUser 을 이용해서 객체 생성
**/

public abstract class OAuth2UserInfo implements ProviderUser {

    private final OAuth2User oAuth2User;
    private final ClientRegistration clientRegistration;


    public OAuth2UserInfo(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        this.oAuth2User = oAuth2User;
        this.clientRegistration = clientRegistration;
    }

    @Override
    public String getPassword() {
        return UUID.randomUUID().toString();
    }


    @Override
    public String getProvider() {
        return clientRegistration.getRegistrationId();
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return (List<? extends GrantedAuthority>) oAuth2User.getAuthorities();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }
}
