package com.emlakjet.purchasing.service.impl;

import com.emlakjet.purchasing.config.security.UserDetailsImpl;
import com.emlakjet.purchasing.dao.authentication.AuthenticationRequestDTO;
import com.emlakjet.purchasing.dao.authentication.AuthenticationResponseDTO;
import com.emlakjet.purchasing.exception.AuthenticationException;
import com.emlakjet.purchasing.service.AuthenticationService;
import com.emlakjet.purchasing.service.JwtService;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AuthenticationService interface that handles user authentication.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticates the user with the provided email and password.
     *
     * @param authenticationRequestDTO the DTO containing the email and password for authentication
     * @return an instance of AuthenticationResponseDTO containing the access token
     * @throws AuthenticationException if the email/password is invalid
     */
    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) throws AuthenticationException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequestDTO.email(), authenticationRequestDTO.password()));
            var token = jwtService.createToken(new UserDetailsImpl(authenticationRequestDTO.email()));
            LOG.info("Authentication successful for email {}", authenticationRequestDTO.email());
            return new AuthenticationResponseDTO(token);
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Invalid email/password supplied");
        }
    }
}
