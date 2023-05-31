package com.reknik.hr.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.List;

public class HrAuthenticationToken extends JwtAuthenticationToken {

    private final List<Long> companyIds;

    public HrAuthenticationToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities,
                                 List<Long> companyIds) {
        super(jwt, authorities);
        this.companyIds = List.copyOf(companyIds);
    }

}
