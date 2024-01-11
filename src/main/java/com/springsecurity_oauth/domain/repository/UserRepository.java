package com.springsecurity_oauth.domain.repository;

import com.springsecurity_oauth.domain.SocialType;
import com.springsecurity_oauth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findBySocialTypeAndOAuthId(SocialType socialType, String oauthId);
}
