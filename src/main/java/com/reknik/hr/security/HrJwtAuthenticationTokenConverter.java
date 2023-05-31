package com.reknik.hr.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public class HrJwtAuthenticationTokenConverter implements Converter<Jwt, HrAuthenticationToken> {
    @Override
    public HrAuthenticationToken convert(Jwt source) {
        List<String> authorityList = source.getClaim("authorities");
        List<Long> companyIds = source.getClaim("companies");
        return new HrAuthenticationToken(source, authorityList.stream().map(SimpleGrantedAuthority::new).toList(),
                companyIds);
    }
}
