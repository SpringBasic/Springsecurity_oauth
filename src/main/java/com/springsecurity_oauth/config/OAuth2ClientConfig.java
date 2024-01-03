package com.springsecurity_oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

//@Configuration
public class OAuth2ClientConfig {

    // 직접 ClientRegistrationRepository 빈을 생성
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(keycloakClientRegistration());
    }

    private ClientRegistration keycloakClientRegistration() {
        return ClientRegistrations.fromIssuerLocation("http://localhost:8080/realms/oauth2")
                .registrationId("keycloak")
                .clientId("oauth2-client-app")
                .clientSecret("YYuqgjv4eC8nEbbxu24rEMlrFd5kSGk6")
                .redirectUri("http://localhost:8081/login/oauth2/code/keycloak")
                .build();
    }
}
