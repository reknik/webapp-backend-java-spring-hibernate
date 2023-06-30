package com.reknik.hr.controller;

import com.reknik.hr.util.AuthenticationHelperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    AuthenticationHelperService authenticationHelperService;

    public UserController(AuthenticationHelperService authenticationHelperService) {
        this.authenticationHelperService = authenticationHelperService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getUserRoles() {
        return ResponseEntity.ok(authenticationHelperService.getCurrentUserRoles());
    }
}
