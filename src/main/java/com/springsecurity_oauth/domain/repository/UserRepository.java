package com.springsecurity_oauth.domain.repository;

import com.springsecurity_oauth.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private Map<String, User> users = new HashMap<>();

    /* 이름으로 유저 검색 */
    public User findByUsername(String username) {
        if(this.users.containsKey(username)) {
            return users.get(username);
        }
        return null;
    }

    /* 유저 저장 */
    public void register(User user) {
        if(this.users.containsKey(user.getUsername())) {
            return;
        }
        this.users.put(user.getUsername(), user);
    }
}
