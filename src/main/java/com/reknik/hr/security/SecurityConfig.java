package com.reknik.hr.security;

import com.reknik.hr.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserServiceImpl userService;
  @Value("${jwt.salt}")
  private String jwtSalt;

  public SecurityConfig(UserServiceImpl userService) {
    this.userService = userService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter =
        new CustomAuthenticationFilter(daoAuthenticationProvider(), jwtSalt);
    customAuthenticationFilter.setFilterProcessesUrl("/api/user/login");
    return http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeHttpRequests()
        .requestMatchers("/api/user/login")
        .permitAll()
        .requestMatchers("/api/user/register")
        .permitAll()//TODO change zis when prezetare is happening
        .requestMatchers("/api/employee/*")
        .hasAnyAuthority(WebAppUserRoles.ADMIN.name(), WebAppUserRoles.USER.name())
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(new CustomAuthorizationFilter(jwtSalt), UsernamePasswordAuthenticationFilter.class)
        .httpBasic()
        .and().csrf()
        .disable().build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

  @Bean
  DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

}
