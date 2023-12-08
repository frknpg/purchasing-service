package com.emlakjet.purchasing.dao.purchasing;

public record PurchasingRequestDTO(String firstName, String lastName,
                                    String email, int amount,
                                    String productName, int billNo) {
}
