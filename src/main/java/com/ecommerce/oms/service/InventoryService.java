package com.ecommerce.oms.service;

public interface InventoryService {

    void reserve(Long productId, Integer qty);

    void release(Long productId, Integer qty);
}
