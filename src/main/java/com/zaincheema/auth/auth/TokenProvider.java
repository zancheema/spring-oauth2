package com.zaincheema.auth.auth;

import java.util.Date;

import com.zaincheema.auth.config.AppProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenProvider {
    private AppProperties appProperties;

    @Autowired
    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date exp = new Date(now.getTime() + appProperties.getTokenExpirationMsec());

        return Jwts.builder()
                .setSubject(String.valueOf(principal.getId()))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, appProperties.getSecretKey())
                .compact();
    }

    public long getIdFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(appProperties.getSecretKey())
            .parseClaimsJws(token)
            .getBody();

        return Long.parseLong(claims.getSubject());
    }
}
