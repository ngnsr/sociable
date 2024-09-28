package com.rr.sociable.config;

import com.rr.sociable.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

//    @Bean
//    public SecurityFilterChain filterSecurity(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll());
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz
                                .anyRequest().authenticated()
                )
                .logout(l -> l.logoutSuccessUrl("/login"))
//                .oauth2ResourceServer((oauth2) -> oauth2
//                        .jwt(withDefaults())
//                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(
                                (userInfoEndpointConfig -> userInfoEndpointConfig
                                        .userService(userService))
                        )
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                )
                .build();
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successListener() {
        return event -> {
            System.out.println("🎉 [%s] %s".formatted(
                    event.getAuthentication().getClass().getSimpleName(),
                    event.getAuthentication().getName()
            ));
        };
    }
}