package com.watchstore.apigateway.businesslayer.Inventory;



import com.watchstore.apigateway.presentationlayer.Inventory.InventoryRequestModel;
import com.watchstore.apigateway.presentationlayer.Inventory.InventoryResponseModel;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    List<InventoryResponseModel> getAllInventories();
    InventoryResponseModel getInventoryByInventoryId(String inventoryId);

    InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel);

    InventoryResponseModel updateInventory(InventoryRequestModel inventoryRequestModel, UUID inventoryId);

    void deleteInventory(UUID inventoryId);
}
