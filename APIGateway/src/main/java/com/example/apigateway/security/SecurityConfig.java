package com.example.apigateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import com.example.common.security.JwtAuthFilter;
import com.example.common.security.JwtService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
                        @Value("${app.security.jwt-secret}") String jwtSecret,
            @Value("${app.security.public-paths:}") String publicPaths
    ) throws Exception {
                JwtService jwtService = new JwtService(jwtSecret);
                JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(jwtService, publicPaths);

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(resolvePublicPaths(publicPaths)).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

        private String[] resolvePublicPaths(String publicPaths) {
                if (!StringUtils.hasText(publicPaths)) {
                        return new String[0];
                }
                return StringUtils.commaDelimitedListToSet(publicPaths)
                                .stream()
                                .map(String::trim)
                                .filter(StringUtils::hasText)
                                .toArray(String[]::new);
        }
}
