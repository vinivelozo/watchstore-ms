package com.watchstore.inventory.mapperlayer.watch;

import com.watchstore.inventory.dataaccesslayer.watch.Watch;
import com.watchstore.inventory.presentationlayer.WatchResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WatchResponseMapper {

    @Mapping(expression = "java(watch.getWatchIdentifier().getReferenceNumber())", target = "referenceNumber")
    @Mapping(expression = "java(watch.getInventoryIdentifier().getInventoryId())",target = "inventoryId")
    WatchResponseModel entityToResponseModel(Watch watch);

    List<WatchResponseModel> entityListToResponseModelList (List<Watch> watches);
}
