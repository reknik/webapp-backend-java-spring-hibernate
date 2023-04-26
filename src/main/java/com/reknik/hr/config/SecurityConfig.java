package com.reknik.hr.config;

import com.reknik.hr.security.HrJwtAuthenticationTokenConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${jwks.uri}")
  private String jwksUri;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.oauth2ResourceServer(
        r -> r.jwt().jwkSetUri(jwksUri)
            .jwtAuthenticationConverter(new HrJwtAuthenticationTokenConverter())
    );
    return http
        .authorizeHttpRequests()
        .anyRequest()
        .authenticated()
        .and().build();
  }

}
