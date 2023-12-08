package com.emlakjet.purchasing.repository;

import com.emlakjet.purchasing.entity.Invoice;
import com.emlakjet.purchasing.entity.InvoiceStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT sum(i.amount) from Invoice i where i.email = :email and i.status = :status")
    Optional<Long> sumAmountByEmailAndStatus(String email, InvoiceStatus status);
}
