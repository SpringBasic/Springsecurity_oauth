package com.springsecurity_oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;

@RestController
public class IndexController {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/oauth/info")
    public String oauthInfo() {
        ClientRegistration clientRegistration
                = this.clientRegistrationRepository.findByRegistrationId("keycloak");

        String clientId = clientRegistration.getClientId();
        System.out.println("clientId = " + clientId);

        String redirectUri = clientRegistration.getRedirectUri();
        System.out.println("redirectUri = " + redirectUri);

        return clientRegistration.getClientName();
    }

    @GetMapping("/user")
    public OAuth2User user(String accessToken) {
        ClientRegistration clientRegistration = this.clientRegistrationRepository.findByRegistrationId("keycloak");

        // OAuth2AccessToken 객체 for 유저 정보 가져오기 API
        OAuth2AccessToken oAuth2AccessToken
                = new OAuth2AccessToken(BEARER, accessToken, Instant.now(), Instant.MAX);

        // 유저 정보 가져오기 요청 객체 생성
        OAuth2UserRequest oAuth2UserRequest = new OAuth2UserRequest(clientRegistration, oAuth2AccessToken);

        // 유저 정보 가져오기 uri from Authorization Server
        String userInfoUri = clientRegistration.getProviderDetails().getUserInfoEndpoint().getUri();

        DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

        // defaultOAuth2UserService 을 통해 OAuth2User 리턴
        return defaultOAuth2UserService.loadUser(oAuth2UserRequest);
    }
}
