package com.emlakjet.purchasing.service.impl;

import com.emlakjet.purchasing.config.PurchasingConfigData;
import com.emlakjet.purchasing.dao.purchasing.PurchasingRequestDTO;
import com.emlakjet.purchasing.entity.Purchasing;
import com.emlakjet.purchasing.repository.PurchasingRepository;
import com.emlakjet.purchasing.service.PurchasingService;
import org.springframework.stereotype.Service;

@Service
public class PurchasingServiceImpl implements PurchasingService {

    private final PurchasingRepository purchasingRepository;
    private final PurchasingConfigData.Limit purchasingLimit;

    public PurchasingServiceImpl(PurchasingRepository purchasingRepository,
                                 PurchasingConfigData purchasingConfigData) {
        this.purchasingRepository = purchasingRepository;
        this.purchasingLimit = purchasingConfigData.getLimit();
    }

    @Override
    public Purchasing addPurchase(PurchasingRequestDTO purchasingRequestDTO) {
        return purchasingRepository.save(new Purchasing(purchasingRequestDTO));
    }
}
