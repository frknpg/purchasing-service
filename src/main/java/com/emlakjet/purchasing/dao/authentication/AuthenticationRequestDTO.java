package com.emlakjet.purchasing.dao.authentication;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDTO(@NotBlank String email, @NotBlank String password) {
}
