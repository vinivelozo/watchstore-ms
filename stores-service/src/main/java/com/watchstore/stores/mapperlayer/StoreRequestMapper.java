package com.watchstore.stores.mapperlayer;

import com.watchstore.stores.dataaccesslayer.Store;
import com.watchstore.stores.dataaccesslayer.StoreIdentifier;
import com.watchstore.stores.presentationlayer.StoreRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoreRequestMapper {

    @Mapping(target = "id", ignore = true)
    Store requestModelToEntity(StoreRequestModel storeRequestModel,
                               StoreIdentifier storeIdentifier);
}
