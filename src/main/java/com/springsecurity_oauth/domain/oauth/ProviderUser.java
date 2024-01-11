package com.springsecurity_oauth.domain.oauth;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

/* 소셜 로그인 타입에 따라 구현될 인터페이스 */
public interface ProviderUser {
    String getId();
    String getUsername();
    String getPassword();
    String getProvider();
    List<? extends GrantedAuthority> getAuthorities();
    Map<String,Object> getAttributes();
}
