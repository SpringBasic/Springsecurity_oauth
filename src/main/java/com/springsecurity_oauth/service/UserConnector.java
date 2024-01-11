package com.springsecurity_oauth.service;

import com.springsecurity_oauth.domain.SocialType;
import com.springsecurity_oauth.domain.User;

import java.util.Optional;

public interface UserConnector {
    Optional<User> findBySocialTypeAndOAuthId(SocialType socialType, String oauthId);

    User saveUser(User user);
}
