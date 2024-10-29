package com.watchstore.apigateway.businesslayer.Inventory;


import com.watchstore.apigateway.domainclientlayer.Inventory.InventoryServiceClient;
import com.watchstore.apigateway.mappinglayer.InventoryResponseMapper;
import com.watchstore.apigateway.presentationlayer.Inventory.InventoryRequestModel;
import com.watchstore.apigateway.presentationlayer.Inventory.InventoryResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryServiceClient inventoryServiceClient;
    private InventoryResponseMapper inventoryResponseMapper;


    public InventoryServiceImpl(InventoryServiceClient inventoryServiceClient, InventoryResponseMapper inventoryResponseMapper) {
        this.inventoryServiceClient = inventoryServiceClient;
        this.inventoryResponseMapper = inventoryResponseMapper;
    }

    @Override
    public List<InventoryResponseModel> getAllInventories(){

        return inventoryResponseMapper.responseModelListToResponseModelList(inventoryServiceClient.getAllInventories());
    }
    @Override
    public InventoryResponseModel getInventoryByInventoryId(String inventoryId) {

        return inventoryResponseMapper.responseModelToResponseModel(inventoryServiceClient.getInventoryByInventoryId(inventoryId));
    }

    @Override
    public InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel){


        return inventoryResponseMapper.responseModelToResponseModel(inventoryServiceClient.addInventory(inventoryRequestModel));
    }

    @Override
    public InventoryResponseModel updateInventory(InventoryRequestModel inventoryRequestModel, UUID inventoryId) {

        return inventoryResponseMapper.responseModelToResponseModel(inventoryServiceClient.updateInventory(inventoryRequestModel, inventoryId));
    }

    @Override
    public void deleteInventory(UUID inventoryId) {
        inventoryServiceClient.deleteInventory(inventoryId);
    }
}
