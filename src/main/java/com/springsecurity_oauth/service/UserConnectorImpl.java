package com.springsecurity_oauth.service;

import com.springsecurity_oauth.domain.SocialType;
import com.springsecurity_oauth.domain.User;
import com.springsecurity_oauth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConnectorImpl implements UserConnector {

    private final UserRepository userRepository;
    @Override
    public Optional<User> findBySocialTypeAndOAuthId(SocialType socialType, String oauthId) {
        return userRepository.findBySocialTypeAndOAuthId(socialType,oauthId);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
