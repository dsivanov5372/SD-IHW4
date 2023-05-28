package com.example.ordermicroservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import lombok.AllArgsConstructor;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors().and()
                   .httpBasic().disable()
                   .csrf().disable()
                   .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                   .and().authorizeHttpRequests((request) -> {
                        request.requestMatchers(HttpMethod.POST, "/order")
                               .hasAnyAuthority("CUSTOMER", "CHEF", "MANAGER")
                               .requestMatchers(HttpMethod.GET, "/order/**")
                               .hasAnyAuthority("CUSTOMER", "CHEF", "MANAGER")
                               .requestMatchers(HttpMethod.GET, "/menu")
                               .hasAnyAuthority("CUSTOMER", "CHEF", "MANAGER")
                               .requestMatchers(HttpMethod.POST, "/dish")
                               .hasAnyAuthority("CHEF", "MANAGER")
                               .requestMatchers(HttpMethod.PATCH, "/dish/**")
                               .hasAnyAuthority("CHEF", "MANAGER")
                               .requestMatchers(HttpMethod.DELETE, "/dish/**")
                               .hasAnyAuthority("CHEF", "MANAGER")
                               .and().addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                   }).build();
    }
}
