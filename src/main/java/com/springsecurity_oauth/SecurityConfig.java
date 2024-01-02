package com.springsecurity_oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.io.IOException;
import java.util.logging.Handler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /* 커스텀한 설정 */
    SecurityFilterChain securityFilterChain1(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        httpSecurity.authorizeHttpRequests(auth -> auth
                        .requestMatchers(mvc.pattern("/**")).permitAll())
                .formLogin(Customizer.withDefaults())
                .apply(new CustomSecurityConfigurer().setFlag(true));
        return httpSecurity.build();
    }

    SecurityFilterChain securityFilterChain2(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        httpSecurity.exceptionHandling(ehl -> ehl.authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                System.out.println("Custom EntryPoint");
            }
        }));
        return httpSecurity.build();
    }

    SecurityFilterChain securityFilterChain3(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

        return httpSecurity.build();
    }

    @Bean
    SecurityFilterChain securityFilterChain4(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        httpSecurity.exceptionHandling(ehl -> ehl.authenticationEntryPoint((AuthenticationEntryPoint) this.customAuthenticationEntryPoint()));


        // 인증 방식 : http basic
        httpSecurity.httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }
}
