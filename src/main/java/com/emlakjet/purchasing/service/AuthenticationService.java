package com.emlakjet.purchasing.service;

import com.emlakjet.purchasing.dao.authentication.AuthenticationRequestDTO;
import com.emlakjet.purchasing.dao.authentication.AuthenticationResponseDTO;

public interface AuthenticationService {
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO);
}
