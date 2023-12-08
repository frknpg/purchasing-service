package com.emlakjet.purchasing.entity;

import com.emlakjet.purchasing.dao.invoice.InvoiceRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Long billNo;

    @Enumerated
    @Column(nullable = false)
    private InvoiceStatus status = InvoiceStatus.DECLINED;

    @CreatedDate
    private LocalDateTime createdTime = LocalDateTime.now();

    public Invoice() {
    }

    public Invoice(InvoiceRequestDTO invoiceRequestDTO) {
        this.firstName = invoiceRequestDTO.firstName();
        this.lastName = invoiceRequestDTO.lastName();
        this.email = invoiceRequestDTO.email();
        this.amount = invoiceRequestDTO.amount();
        this.productName = invoiceRequestDTO.productName();
        this.billNo = invoiceRequestDTO.billNo();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getBillNo() {
        return billNo;
    }

    public void setBillNo(Long billNo) {
        this.billNo = billNo;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
