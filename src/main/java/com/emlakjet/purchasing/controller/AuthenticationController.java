package com.emlakjet.purchasing.controller;

import com.emlakjet.purchasing.dao.authentication.AuthenticationRequestDTO;
import com.emlakjet.purchasing.dao.authentication.AuthenticationResponseDTO;
import com.emlakjet.purchasing.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/v1/auth/login")
    public AuthenticationResponseDTO authenticate(@RequestBody @Valid AuthenticationRequestDTO authenticationRequestDTO) {
        return authenticationService.authenticate(authenticationRequestDTO);
    }
}
