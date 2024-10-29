package com.watchstore.inventory.businesslayer;

import com.watchstore.inventory.dataaccesslayer.inventory.Inventory;
import com.watchstore.inventory.dataaccesslayer.inventory.InventoryIdentifier;
import com.watchstore.inventory.dataaccesslayer.inventory.InventoryRepository;
import com.watchstore.inventory.mapperlayer.inventory.InventoryRequestMapper;
import com.watchstore.inventory.mapperlayer.inventory.InventoryResponseMapper;
import com.watchstore.inventory.presentationlayer.InventoryRequestModel;
import com.watchstore.inventory.presentationlayer.InventoryResponseModel;
import com.watchstore.inventory.utils.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InventoryServiceImpl implements InventoryService{

    private InventoryRepository inventoryRepository;
    private InventoryResponseMapper inventoryResponseMapper;
    private InventoryRequestMapper inventoryRequestMapper;


    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryResponseMapper inventoryResponseMapper, InventoryRequestMapper inventoryRequestMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryResponseMapper = inventoryResponseMapper;
        this.inventoryRequestMapper = inventoryRequestMapper;
    }

    @Override
    public List<InventoryResponseModel> getAllInventories(){

        List<Inventory> inventoryList = inventoryRepository.findAll();

        return inventoryResponseMapper.entityListToResponseModelList(inventoryList);
    }
    @Override
    public InventoryResponseModel getInventoryById(String inventoryId) {

        Inventory foundInventory= inventoryRepository.findInventoryByInventoryIdentifier_InventoryId(inventoryId);
        if(foundInventory == null){
            throw new NotFoundException("Unknow inventory" + inventoryId);
        }

        return inventoryResponseMapper.entityToResponseModel(foundInventory);
    }

    @Override
    public InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel){

        Inventory inventory = inventoryRequestMapper.requestModelToEntity(inventoryRequestModel,
                new InventoryIdentifier());

        return inventoryResponseMapper.entityToResponseModel(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponseModel updateInventory(InventoryRequestModel inventoryRequestModel, UUID InventoryId) {
        Inventory existingInventory = inventoryRepository.findInventoryByInventoryIdentifier_InventoryId(InventoryId.toString());
        if (existingInventory == null){
            throw new NotFoundException("Cannot update id: " + InventoryId);
        }

        Inventory inventory = inventoryRequestMapper.requestModelToEntity(inventoryRequestModel,
                existingInventory.getInventoryIdentifier());
        inventory.setId(existingInventory.getId());


        return inventoryResponseMapper.entityToResponseModel(inventoryRepository.save(inventory));
    }

    @Override
    public void deleteInventory(UUID InventoryId) {
        Inventory inventory = inventoryRepository.findInventoryByInventoryIdentifier_InventoryId(InventoryId.toString());
        if (inventory == null) {
            throw new NotFoundException("No inventory found with ID: " + InventoryId);
        }
        inventoryRepository.delete(inventory);
    }
}
