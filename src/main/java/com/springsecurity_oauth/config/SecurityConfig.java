package com.springsecurity_oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


// proxyBeanMethods is false : 각 빈 메소드 호출마다 새로운 빈 인스턴스를 생성, 메모리 소비 증가 가능성
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain4(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers("/loginPage").permitAll()
                .anyRequest().authenticated()
        );
        // oauth2 login 관련
        // login 페이지 설정
        httpSecurity.oauth2Login(oauth2 -> oauth2.loginPage("/loginPage"));
        httpSecurity.oauth2Client(Customizer.withDefaults());

        return httpSecurity.build();
    }

}
