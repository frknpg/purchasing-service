package com.emlakjet.purchasing.service;

import com.emlakjet.purchasing.dao.purchasing.PurchasingRequestDTO;
import com.emlakjet.purchasing.entity.Purchasing;

public interface PurchasingService {
    Purchasing addPurchase(PurchasingRequestDTO purchasingRequestDTO);
}
