package com.emlakjet.purchasing.service;

import com.emlakjet.purchasing.TestProfileConfiguration;
import com.emlakjet.purchasing.config.PurchasingConfigData;
import com.emlakjet.purchasing.dao.invoice.InvoiceRequestDTO;
import com.emlakjet.purchasing.entity.Invoice;
import com.emlakjet.purchasing.entity.InvoiceStatus;
import com.emlakjet.purchasing.repository.InvoiceRepository;
import com.emlakjet.purchasing.service.impl.InvoiceServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class InvoiceServiceTest implements TestProfileConfiguration {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PurchasingConfigData purchasingConfigData;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private InvoiceService invoiceService;

    @BeforeEach
    public void setup() {
        this.invoiceService = new InvoiceServiceImpl(invoiceRepository, purchasingConfigData);
    }

    @Test
    @Transactional
    public void addInvoice_withSufficientLimit_getAcceptedStatus() {
        Invoice resultInvoice = getInvoice(200L);
        assertNotNull(resultInvoice);
        assertThat(resultInvoice.getStatus()).isEqualByComparingTo(InvoiceStatus.APPROVED);
    }

    @Test
    @Transactional
    public void addInvoice_withOverLimit_getDeclinedStatus() {
        Invoice resultInvoice = getInvoice(201L);
        assertNotNull(resultInvoice);
        assertThat(resultInvoice.getStatus()).isEqualByComparingTo(InvoiceStatus.DECLINED);
    }

    @Test
    @Transactional
    public void addTwoInvoice_withSameUserSufficientLimit_getAcceptedStatus() {
        Invoice resultInvoice = getInvoice(100L);
        assertNotNull(resultInvoice);
        assertThat(resultInvoice.getStatus()).isEqualByComparingTo(InvoiceStatus.APPROVED);

        resultInvoice = getInvoice(100L);
        assertNotNull(resultInvoice);
        assertThat(resultInvoice.getStatus()).isEqualByComparingTo(InvoiceStatus.APPROVED);
    }

    @Test
    @Transactional
    public void addTwoInvoice_withSameUserOverLimit_getDeclinedStatus() {
        Invoice resultInvoice = getInvoice(101L);
        assertNotNull(resultInvoice);
        assertThat(resultInvoice.getStatus()).isEqualByComparingTo(InvoiceStatus.APPROVED);

        resultInvoice = getInvoice(101L);
        assertNotNull(resultInvoice);
        assertThat(resultInvoice.getStatus()).isEqualByComparingTo(InvoiceStatus.DECLINED);
    }

    @Test
    @Transactional
    public void addTwoInvoice_withDifferentUserSufficientLimit_getAcceptedStatus() {
        Invoice resultInvoice = getInvoice(101L);
        assertNotNull(resultInvoice);
        assertThat(resultInvoice.getStatus()).isEqualByComparingTo(InvoiceStatus.APPROVED);

        resultInvoice = getInvoice(101L, "johndoe@mail.local");
        assertNotNull(resultInvoice);
        assertThat(resultInvoice.getStatus()).isEqualByComparingTo(InvoiceStatus.APPROVED);
    }

    @Test
    public void getInvoices_withAddedData_getOneElement() {
        var invoice = new Invoice(new InvoiceRequestDTO(
                "John", "Doe",
                "john@doe.com", 199L, "TR0001", 123L
        ));
        invoice.setStatus(InvoiceStatus.APPROVED);
        invoiceRepository.save(invoice);

        var result = invoiceService.getPurchases(Pageable.ofSize(20), invoice);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }


    @Test
    public void getInvoices_withNoData_getEmptyContent() {
        var result = invoiceService.getPurchases(Pageable.ofSize(20), new Invoice());
        assertThat(result.getTotalElements()).isEqualTo(0);
    }

    @Transactional
    public Invoice getInvoice(Long amount) {
        var invoiceRequestDTO = new InvoiceRequestDTO("John", "Doe",
                "john@doe.com",
                amount, "TR0001",
                123L
        );
        return invoiceService.addPurchase(invoiceRequestDTO);
    }

    @Transactional
    public Invoice getInvoice(Long amount, String email) {
        var invoiceRequestDTO = new InvoiceRequestDTO("John", "Doe",
                email, amount, "TR0001",
                123L
        );
        return invoiceService.addPurchase(invoiceRequestDTO);
    }

    @AfterEach
    void clearTable() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "invoice");
    }
}
