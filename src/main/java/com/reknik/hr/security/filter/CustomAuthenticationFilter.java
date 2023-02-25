package com.reknik.hr.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.reknik.hr.entity.WebAppUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final DaoAuthenticationProvider daoAuthenticationProvider;
  private final String jwtSalt;

  public CustomAuthenticationFilter(DaoAuthenticationProvider daoAuthenticationProvider, String jwtSalt) {
    this.daoAuthenticationProvider = daoAuthenticationProvider;
    this.jwtSalt = jwtSalt;
    this.setFilterProcessesUrl("/api/user/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
        password);
    return daoAuthenticationProvider.authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain
      , Authentication authResult) {
    WebAppUser user = (WebAppUser) authResult.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256(jwtSalt.getBytes(StandardCharsets.UTF_8));
    String accessToken = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(Instant.now().plus(10, ChronoUnit.HOURS).toEpochMilli()))
        .withIssuer(request.getRequestURL().toString())
        .withClaim("role",
            user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
        .sign(algorithm);
    String refreshToken = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()))
        .withIssuer(request.getRequestURL().toString())
        .sign(algorithm);
    response.setHeader("access_token", accessToken);
    response.setHeader("refresh_token", refreshToken);
  }
}
