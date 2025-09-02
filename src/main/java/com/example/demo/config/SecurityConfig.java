package com.example.demo.config;

import com.example.demo.service.auth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**")
                .permitAll()
                .requestMatchers("/api/v1/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )
            );

        return http.build();
    }

}
