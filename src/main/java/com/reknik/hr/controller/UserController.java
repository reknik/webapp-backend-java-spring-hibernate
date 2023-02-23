package com.reknik.hr.controller;

import com.reknik.hr.entity.WebAppUser;
import com.reknik.hr.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserServiceImpl userService;

  public UserController(@Qualifier("userService") UserServiceImpl userService) {
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
