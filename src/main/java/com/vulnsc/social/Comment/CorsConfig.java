package com.vulnsc.social.Comment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Configuration
public class CorsConfig {

    @Value("${cors.allowedOriginsRegex}")
    private String allowedOriginsRegex;


    // Configures and creates a CorsFilter bean for handling CORS (Cross-Origin Resource Sharing)
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // Set the allowed origins using the provided regex pattern
        config.setAllowedOrigins(Collections.singletonList(allowedOriginsRegex));
        // Allow all HTTP methods and headers
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        // Allow credentials
        config.setAllowCredentials(true);
        // Register the CORS configuration for all URLs
        source.registerCorsConfiguration("/**", config);

        // Create a custom CorsFilter to dynamically update the allowed origins based on the request's Origin header
        return new CorsFilter(source) {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String origin = request.getHeader("Origin");
                if (origin != null && origin.matches(allowedOriginsRegex)) {
                    config.setAllowedOrigins(Collections.singletonList(origin));
                }
                super.doFilterInternal(request, response, filterChain);
            }
        };
    }
}




