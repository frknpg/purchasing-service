package com.emlakjet.purchasing.dao.purchasing;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PurchasingRequestDTO(@NotBlank String firstName,
                                   @NotBlank String lastName,
                                   @NotBlank @Email String email,
                                   @NotNull Integer amount,
                                   @NotBlank String productName,
                                   @NotNull Integer billNo) {
}
