package com.reknik.webAppDemoBackend.controller;

import com.reknik.webAppDemoBackend.entity.WebAppUser;
import com.reknik.webAppDemoBackend.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  @Qualifier("userService")
  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody WebAppUser user) {
    userService.saveUser(user);
    return new ResponseEntity<>("User registered", HttpStatus.OK);
  }


  @GetMapping("/hello")
  public ResponseEntity<?> test() {
    return new ResponseEntity<>("hello", HttpStatus.OK);
  }
}
