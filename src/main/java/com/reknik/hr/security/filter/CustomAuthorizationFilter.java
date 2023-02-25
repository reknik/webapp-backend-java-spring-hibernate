package com.reknik.hr.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

public class CustomAuthorizationFilter extends OncePerRequestFilter {


  private final List<String> excludedEndpoints = List.of("/api/user/login", "/api/user/register");
  private final String jwtSalt;

  public CustomAuthorizationFilter(String jwtSalt) {
    this.jwtSalt = jwtSalt;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return excludedEndpoints.contains(request.getRequestURI());
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      response.setHeader("ERROR", "");
      response.sendError(HttpServletResponse.SC_FORBIDDEN);
      return;
    }
    String token = authorizationHeader.substring("Bearer ".length());
    Algorithm algorithm = Algorithm.HMAC256(jwtSalt.getBytes(StandardCharsets.UTF_8));
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT decodedJWT = verifier.verify(token);
    String username = decodedJWT.getSubject();
    List<String> roles = decodedJWT.getClaim("role").asList(String.class);
    Collection<SimpleGrantedAuthority> authorities =
        roles.stream().map(SimpleGrantedAuthority::new).toList();
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
        null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    filterChain.doFilter(request, response);
  }
}

