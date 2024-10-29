package com.watchstore.inventory.businesslayer;

import com.watchstore.inventory.presentationlayer.InventoryRequestModel;
import com.watchstore.inventory.presentationlayer.InventoryResponseModel;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    List<InventoryResponseModel> getAllInventories();
    InventoryResponseModel getInventoryById(String inventoryId);

    InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel);

    InventoryResponseModel updateInventory(InventoryRequestModel inventoryRequestModel, UUID InventoryId);

    void deleteInventory(UUID InventoryId);
}
