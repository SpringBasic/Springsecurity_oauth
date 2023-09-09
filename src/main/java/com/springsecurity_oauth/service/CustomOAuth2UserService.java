package com.springsecurity_oauth.service;

import com.springsecurity_oauth.domain.SocialType;
import com.springsecurity_oauth.domain.User;
import com.springsecurity_oauth.domain.oauth.GoogleOAuth2UserInfo;
import com.springsecurity_oauth.domain.oauth.KakaoOAuth2UserInfo;
import com.springsecurity_oauth.domain.oauth.NaverOAuth2UserInfo;
import com.springsecurity_oauth.domain.oauth.OAuth2UserInfo;
import com.springsecurity_oauth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * - OAuth2UserRequest 을 통해 사용자 정보 요청
 * - 사용자 정보 저장
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private static final String GOOGLE = "google";
    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";


    private final UserConnector userConnector;
    /**
     * < OAuth2UserRequest >
     * - OAuth2AccessToken
     * - ClientRegistration
     * - Map<String,Map> additionalParameters
     **/
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegateOAuth2UserService = new DefaultOAuth2UserService();
        /* 사용자 정보 요청 */

        /** < OAuth2User >
         * - Map<String,Object> attributes
         * - Set<GrantedAuthority> authorities
         * - String nameAttributeKey
         **/
        OAuth2User oAuth2User = delegateOAuth2UserService.loadUser(userRequest);

        String registrationId = clientRegistration.getRegistrationId();
        SocialType socialType = getSocialType(registrationId);

        OAuth2UserInfo oAuth2UserInfo = getOAuth2UserInfo(socialType, oAuth2User, clientRegistration);

        User createdUser = getUser(oAuth2UserInfo, socialType);

        // CustomOAuthUser 반환

        return null;
    }

    /* SocialType 과 OAuthId 을 가진 user 조회 */
    private User getUser(OAuth2UserInfo oAuth2UserInfo, SocialType socialType) {
        User user = userConnector.findBySocialTypeAndOAuthId(socialType, oAuth2UserInfo.getId())
                .orElse(null);

        if(user == null) {
            return saveUser(oAuth2UserInfo, socialType);
        }
        return user;
    }

    /* 해당하는 회원이 존재하지 않는 경우 저장 */
    private User saveUser(OAuth2UserInfo oAuth2UserInfo, SocialType socialType) {
        return userConnector.saveUser(oAuth2UserInfo.toEntity(socialType));
    }

    /* Social Type 에 해당하는 OAuth2UserInfo 반환 */
    private OAuth2UserInfo getOAuth2UserInfo(SocialType socialType, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        if (socialType == SocialType.GOOGLE) {
            return new GoogleOAuth2UserInfo(oAuth2User, clientRegistration);
        } else if (socialType == SocialType.NAVER) {
            return new NaverOAuth2UserInfo(oAuth2User, clientRegistration);
        } else {
            return new KakaoOAuth2UserInfo(oAuth2User, clientRegistration);
        }
    }

    /* RegistrationId 에 따른 SocialType 반환 */
    private SocialType getSocialType(String registrationId) {
        if (NAVER.equals(registrationId)) {
            return SocialType.NAVER;
        } else if (KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        } else if (GOOGLE.equals(registrationId)) {
            return SocialType.GOOGLE;
        }
        throw new RuntimeException("일치하는 소셜 로그인 타입이 존재하지 않습니다.");
    }
}
