package com.emlakjet.purchasing.controller.dto;

public record PurchasingRequestDTO(String firstName, String lastName,
                                    String email, int amount,
                                    String productName, int billNo) {
}
