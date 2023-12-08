package com.emlakjet.purchasing.repository;

import com.emlakjet.purchasing.TestProfileConfiguration;
import com.emlakjet.purchasing.dao.invoice.InvoiceRequestDTO;
import com.emlakjet.purchasing.entity.Invoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class InvoiceRepositoryTest implements TestProfileConfiguration {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void persistInvoice_withCorrectLimitTestEntity_checkCount() {
        var convertedLink = new Invoice(new InvoiceRequestDTO(
                "John", "Doe",
                "john@doe.com", 199L, "TR0001", 123L
        ));

        testEntityManager.persist(convertedLink);

        var count = invoiceRepository.count();
        assertEquals(1, count);
    }
}
