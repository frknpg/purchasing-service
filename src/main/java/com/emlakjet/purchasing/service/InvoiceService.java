package com.emlakjet.purchasing.service;

import com.emlakjet.purchasing.dao.invoice.InvoiceRequestDTO;
import com.emlakjet.purchasing.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceService {
    Invoice addPurchase(InvoiceRequestDTO invoiceRequestDTO);

    Page<Invoice> getPurchases(Pageable pageable);
}
