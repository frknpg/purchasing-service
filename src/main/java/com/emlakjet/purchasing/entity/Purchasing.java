package com.emlakjet.purchasing.entity;

import com.emlakjet.purchasing.dao.purchasing.PurchasingRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "purchasing",
        indexes = @Index(columnList = "billNo"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"billNo"})}
)
public class Purchasing {

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
    private Integer amount;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer billNo;

    @CreatedDate
    private LocalDateTime createdTime = LocalDateTime.now();

    public Purchasing() {
    }

    public Purchasing(PurchasingRequestDTO purchasingRequestDTO) {
        this.firstName = purchasingRequestDTO.firstName();
        this.lastName = purchasingRequestDTO.lastName();
        this.email = purchasingRequestDTO.email();
        this.amount = purchasingRequestDTO.amount();
        this.productName = purchasingRequestDTO.productName();
        this.billNo = purchasingRequestDTO.billNo();
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getBillNo() {
        return billNo;
    }

    public void setBillNo(Integer billNo) {
        this.billNo = billNo;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
