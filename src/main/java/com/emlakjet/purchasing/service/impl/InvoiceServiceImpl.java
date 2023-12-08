package com.emlakjet.purchasing.service.impl;

import com.emlakjet.purchasing.config.PurchasingConfigData;
import com.emlakjet.purchasing.dao.invoice.InvoiceRequestDTO;
import com.emlakjet.purchasing.entity.InvoiceStatus;
import com.emlakjet.purchasing.entity.Invoice;
import com.emlakjet.purchasing.repository.InvoiceRepository;
import com.emlakjet.purchasing.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service implementation for managing invoices.
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceRepository invoiceRepository;
    private final PurchasingConfigData.Limit purchasingLimit;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              PurchasingConfigData purchasingConfigData) {
        this.invoiceRepository = invoiceRepository;
        this.purchasingLimit = purchasingConfigData.getLimit();
    }

    /**
     * Adds a purchase to the invoice repository and returns the new invoice.
     *
     * @param invoiceRequestDTO the invoice request DTO containing the purchase details
     * @return the newly created invoice
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Invoice addPurchase(InvoiceRequestDTO invoiceRequestDTO) {
        var purchasing = new Invoice(invoiceRequestDTO);

        var totalLimit = invoiceRepository
                .sumAmountByEmailAndStatus(purchasing.getEmail(), InvoiceStatus.APPROVED).orElse(0L);
        if (totalLimit + purchasing.getAmount() > purchasingLimit.getMax()) {
            LOG.warn("Limit overpassed for email: {}", purchasing.getEmail());
            purchasing.setStatus(InvoiceStatus.DECLINED);
        } else {
            LOG.info("Purchase approved for email: {}", purchasing.getEmail());
            purchasing.setStatus(InvoiceStatus.APPROVED);
        }
        return invoiceRepository.save(purchasing);
    }

    /**
     * Retrieves a page of purchases from the invoice repository.
     *
     * @param pageable the pagination information
     * @return a page of invoices containing the purchases
     */
    @Override
    public Page<Invoice> getPurchases(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }
}
