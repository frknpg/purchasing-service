package com.emlakjet.purchasing.dao.purchasing;

import com.emlakjet.purchasing.entity.Purchasing;

public record PurchasingResponseDTO(long id, String firstName, String lastName,
                                    String email, int amount,
                                    String productName, int billNo) {

    public PurchasingResponseDTO(Purchasing purchasing) {
        this(purchasing.getId(), purchasing.getFirstName(),
                purchasing.getLastName(), purchasing.getEmail(),
                purchasing.getAmount(), purchasing.getProductName(),
                purchasing.getBillNo());
    }
}
