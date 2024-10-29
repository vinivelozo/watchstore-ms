package com.watchstore.inventory.mapperlayer.watch;

import com.watchstore.inventory.dataaccesslayer.inventory.InventoryIdentifier;
import com.watchstore.inventory.dataaccesslayer.watch.Watch;
import com.watchstore.inventory.dataaccesslayer.watch.WatchIdentifier;
import com.watchstore.inventory.presentationlayer.WatchRequestModel;
import com.watchstore.inventory.presentationlayer.WatchRequestModelUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WatchRequestMapper {

    @Mapping(target = "id", ignore = true)
    Watch requestModelToEntity(WatchRequestModel watchRequestModel, WatchIdentifier watchIdentifier, InventoryIdentifier inventoryIdentifier);

    @Mapping(target = "id", ignore = true)
    Watch requestModelUpdateToEntity(WatchRequestModelUpdate watchRequestModelupdate, WatchIdentifier watchIdentifier, InventoryIdentifier inventoryIdentifier);
}
