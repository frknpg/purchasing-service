package com.emlakjet.purchasing.dao.invoice;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InvoiceRequestDTO(@NotBlank String firstName,
                                @NotBlank String lastName,
                                @NotBlank @Email String email,
                                @NotNull Long amount,
                                @NotBlank String productName,
                                @NotNull Long billNo) {
}
