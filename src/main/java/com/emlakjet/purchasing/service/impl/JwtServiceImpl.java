package com.emlakjet.purchasing.service.impl;

import com.emlakjet.purchasing.config.JwtConfigData;
import com.emlakjet.purchasing.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    final JwtConfigData jwtConfigData;

    public JwtServiceImpl(JwtConfigData jwtConfigData) {
        this.jwtConfigData = jwtConfigData;
    }

    @Override
    public Claims extractAllClaims(String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    @Override
    public String createToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtConfigData.getExpiration()))
                .signWith(getSecretKey())
                .compact();
    }

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfigData.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
