package com.segatto_builder.tinyvillagehub.security;

import com.segatto_builder.tinyvillagehub.config.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Defines the security filter chain (rules for access).
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        http
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity in API development
                .authorizeHttpRequests(auth -> auth
                        // Allow POST requests to /api/users/register (for new users)
                        .requestMatchers("/api/auth/register").permitAll()
                        // Allow POST requests to /login (for basic form/API login)
                        .requestMatchers("/api/auth/authenticate").permitAll()
                        // Allow GET requests for the public item listing
                        .requestMatchers(HttpMethod.GET, "/api/items/available").permitAll()
                        // Allow GET requests for item images
                        .requestMatchers("/uploads/**").permitAll()
                        // Require authentication for all other requests

                        .anyRequest().authenticated()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Defines the password encoder for hashing passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt is the standard, secure, and recommended hashing algorithm
        return new BCryptPasswordEncoder();
    }

    // Inside com.segatto_builder.tinyvillagehub.security.SecurityConfig.java

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // ⚠️ current frontend port (e.g., 5173)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:3000"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}