package com.reknik.webAppDemoBackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.reknik.webAppDemoBackend.entity.WebAppUser;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


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
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    return daoAuthenticationProvider.authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
    WebAppUser user = (WebAppUser) authResult.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256(jwtSalt.getBytes(StandardCharsets.UTF_8));
    String accessToken = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(Instant.now().plus(365, ChronoUnit.MINUTES).toEpochMilli()))
        .withIssuer(request.getRequestURL().toString())
        .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .sign(algorithm);
    String refreshToken = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(Instant.now().plus(365, ChronoUnit.DAYS).toEpochMilli()))
        .withIssuer(request.getRequestURL().toString())
        .sign(algorithm);
    response.setHeader("access_token", accessToken);
    response.setHeader("refresh_token", refreshToken);
  }
}
