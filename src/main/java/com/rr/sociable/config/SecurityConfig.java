package com.rr.sociable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    private final OidcUserService userService;

    public SecurityConfig(OidcUserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(l -> l.logoutSuccessUrl("/login"))
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(
                                (userInfoEndpointConfig -> userInfoEndpointConfig
                                        .oidcUserService(userService)
                                )
                        )
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(withDefaults()))
                .build();
    }
}