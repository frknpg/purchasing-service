package com.emlakjet.purchasing.dao.invoice;

import com.emlakjet.purchasing.entity.InvoiceStatus;
import com.emlakjet.purchasing.entity.Invoice;

public record InvoiceResponseDTO(Long id, String firstName, String lastName,
                                 String email, Long amount,
                                 String productName, Long billNo,
                                 InvoiceStatus status) {

    public InvoiceResponseDTO(Invoice invoice) {
        this(invoice.getId(), invoice.getFirstName(),
                invoice.getLastName(), invoice.getEmail(),
                invoice.getAmount(), invoice.getProductName(),
                invoice.getBillNo(), invoice.getStatus());
    }
}
