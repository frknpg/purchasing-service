package com.emlakjet.purchasing.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    Claims extractAllClaims(String token) throws JwtException;

    String createToken(UserDetails userDetails);
}
