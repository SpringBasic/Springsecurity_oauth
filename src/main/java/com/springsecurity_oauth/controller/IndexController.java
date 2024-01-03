package com.springsecurity_oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
