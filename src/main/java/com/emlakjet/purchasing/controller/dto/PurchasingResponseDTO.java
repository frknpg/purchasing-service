package com.emlakjet.purchasing.controller.dto;

public record PurchasingResponseDTO(int id, String firstName, String lastName,
                                    String email, int amount,
                                    String productName, int billNo) {
}
