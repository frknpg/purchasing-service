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


/**
 * This class is an implementation of the JwtService interface. It provides methods for extracting claims from a JWT token and for creating a new JWT token.
 */
@Service
public class JwtServiceImpl implements JwtService {

    final JwtConfigData jwtConfigData;

    public JwtServiceImpl(JwtConfigData jwtConfigData) {
        this.jwtConfigData = jwtConfigData;
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token The JWT token from which to extract the claims.
     * @return The claims extracted from the JWT token.
     * @throws JwtException If an error occurs while parsing the JWT token.
     */
    @Override
    public Claims extractAllClaims(String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    /**
     * Creates a JWT token based on the provided UserDetails.
     *
     * @param userDetails The UserDetails object containing the user's information.
     * @return The created JWT token.
     */
    @Override
    public String createToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtConfigData.getExpiration()))
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * Retrieves the secret key used for signing the JWT token.
     *
     * @return The secret key used for signing the JWT token.
     */
    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfigData.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
