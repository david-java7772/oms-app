package com.ecommerce.oms.service.impl;

import com.ecommerce.oms.entity.Inventory;
import com.ecommerce.oms.exception.NotFoundException;
import com.ecommerce.oms.repo.InventoryRepository;
import com.ecommerce.oms.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepo;


    @Override
    @Transactional
    public void reserve(Long productId, Integer qty) {

        Inventory inv = inventoryRepo.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productId));

        if (inv.getAvailableQty() < qty) {
            throw new RuntimeException("Insufficient stock for product: " + productId);
        }

        inv.setAvailableQty(inv.getAvailableQty() - qty);
        inv.setReservedQty(inv.getReservedQty() + qty);
        inv.setUpdatedAt(Instant.now());

        inventoryRepo.save(inv);
    }


    @Override
    @Transactional
    public void release(Long productId, Integer qty) {

        Inventory inv = inventoryRepo.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productId));

        inv.setAvailableQty(inv.getAvailableQty() + qty);
        inv.setReservedQty(Math.max(0, inv.getReservedQty() - qty));
        inv.setUpdatedAt(Instant.now());

        inventoryRepo.save(inv);
    }
}
