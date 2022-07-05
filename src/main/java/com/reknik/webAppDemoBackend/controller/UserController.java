package com.reknik.webAppDemoBackend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.reknik.webAppDemoBackend.entity.WebAppUser;
import com.reknik.webAppDemoBackend.service.UserServiceImpl;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserServiceImpl userService;
  @Value("${jwt.salt}")
  private String jwtsalt;

  @Autowired
  public UserController(@Qualifier("userService") UserServiceImpl userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody WebAppUser user) {
    userService.saveUser(user);
    return new ResponseEntity<>("User registered", HttpStatus.OK);
  }

  @GetMapping("/refreshToken")
  public ResponseEntity<?> refreshToken(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      try {
        String token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(jwtsalt.getBytes(StandardCharsets.UTF_8));
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        WebAppUser user = userService.getUser(username).orElseThrow(IllegalAccessError::new);
        String accessToken = JWT.create()
            .withSubject(user.getUsername())
            .withExpiresAt(new Date(Instant.now().plus(365, ChronoUnit.DAYS).toEpochMilli()))
            .withIssuer(request.getRequestURL().toString())
            .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .sign(algorithm);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("accessToken",
            accessToken);
        return ResponseEntity.ok().headers(responseHeaders).build();
      } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
      }
    } else {
      return new ResponseEntity<>("Refresh token missing", HttpStatus.FORBIDDEN);
    }
  }


  @GetMapping("/hello")
  public ResponseEntity<?> test() {
    return new ResponseEntity<>("hello", HttpStatus.OK);
  }
}
