package com.emlakjet.purchasing.repository;

import com.emlakjet.purchasing.entity.Purchasing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasingRepository extends JpaRepository<Purchasing, Long> {
}
