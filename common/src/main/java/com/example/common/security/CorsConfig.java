package com.example.common.security;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${app.security.cors.allowed-origins:http://localhost:3000}") String allowedOrigins,
            @Value("${app.security.cors.allowed-methods:GET,POST,PUT,PATCH,DELETE,OPTIONS}") String allowedMethods,
            @Value("${app.security.cors.allowed-headers:*}") String allowedHeaders,
            @Value("${app.security.cors.exposed-headers:Authorization}") String exposedHeaders
    ) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(parseCsv(allowedOrigins));
        config.setAllowedMethods(parseCsv(allowedMethods));
        config.setAllowedHeaders(parseCsv(allowedHeaders));
        config.setExposedHeaders(parseCsv(exposedHeaders));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private List<String> parseCsv(String value) {
        return StringUtils.commaDelimitedListToSet(value)
                .stream()
                .map(String::trim)
                .filter(StringUtils::hasText)
                .toList();
    }
}
