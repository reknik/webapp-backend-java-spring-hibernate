package com.reknik.hr.util;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationHelperService {

    public List<Long> getCurrentUserCompanies() {
        Jwt authentication = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (ArrayList<Long>) authentication.getClaims().get("companies");
    }

    public List<String> getCurrentUserRoles() {
        Jwt authentication = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ArrayList<>((ArrayList) authentication.getClaims().get("roles"));
    }
}
