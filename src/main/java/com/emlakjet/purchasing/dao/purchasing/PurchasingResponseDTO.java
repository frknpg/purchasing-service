package com.emlakjet.purchasing.dao.purchasing;

public record PurchasingResponseDTO(int id, String firstName, String lastName,
                                    String email, int amount,
                                    String productName, int billNo) {
}
