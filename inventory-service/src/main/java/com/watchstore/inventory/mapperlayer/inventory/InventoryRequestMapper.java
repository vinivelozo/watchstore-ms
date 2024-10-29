package com.watchstore.inventory.mapperlayer.inventory;

import com.watchstore.inventory.dataaccesslayer.inventory.Inventory;
import com.watchstore.inventory.dataaccesslayer.inventory.InventoryIdentifier;
import com.watchstore.inventory.presentationlayer.InventoryRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface InventoryRequestMapper {

    @Mapping(target = "id", ignore = true)
    Inventory requestModelToEntity(InventoryRequestModel inventoryRequestModel,
                                   InventoryIdentifier inventoryIdentifier);
}
