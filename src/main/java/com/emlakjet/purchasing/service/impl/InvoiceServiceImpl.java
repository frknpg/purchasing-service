package com.emlakjet.purchasing.service.impl;

import com.emlakjet.purchasing.config.PurchasingConfigData;
import com.emlakjet.purchasing.dao.invoice.InvoiceRequestDTO;
import com.emlakjet.purchasing.entity.Invoice;
import com.emlakjet.purchasing.entity.InvoiceStatus;
import com.emlakjet.purchasing.repository.InvoiceRepository;
import com.emlakjet.purchasing.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;


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
    @Retryable(retryFor = {PessimisticLockingFailureException.class}, maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public Invoice addPurchase(InvoiceRequestDTO invoiceRequestDTO) {
        var invoice = new Invoice(invoiceRequestDTO);
        updateInvoiceStatus(invoice);
        return invoiceRepository.save(invoice);
    }

    /**
     * Retrieves a page of purchases from the invoice repository.
     *
     * @param pageable the pagination information
     * @return a page of invoices containing the purchases
     */
    @Override
    public Page<Invoice> getPurchases(Pageable pageable, Invoice filter) {
        return invoiceRepository.findAll(buildExample(filter), pageable);
    }

    private Example<Invoice> buildExample(Invoice filter) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("email", exact())
                .withMatcher("status", exact())
                .withMatcher("amount", exact())
                .withMatcher("productName", exact())
                .withMatcher("billNo", exact())
                .withIgnorePaths("id", "firstName", "lastName", "createdTime");
        return Example.of(filter, matcher);
    }

    private void updateInvoiceStatus(Invoice invoice) {
        var totalApprovedAmount = invoiceRepository
                .sumAmountByEmailAndStatus(invoice.getEmail(), InvoiceStatus.APPROVED).orElse(0L);

        boolean isOverLimit = totalApprovedAmount + invoice.getAmount() > purchasingLimit.getMax();

        if (isOverLimit) {
            LOG.warn("Limit overpassed for email: {}", invoice.getEmail());
            invoice.setStatus(InvoiceStatus.DECLINED);
        } else {
            LOG.info("Purchase approved for email: {}", invoice.getEmail());
            invoice.setStatus(InvoiceStatus.APPROVED);
        }
    }
}
