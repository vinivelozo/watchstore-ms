package com.watchstore.stores.businesslayer;

import com.watchstore.stores.presentationlayer.StoreRequestModel;
import com.watchstore.stores.presentationlayer.StoreResponseModel;

import java.util.List;
import java.util.UUID;

public interface StoreService {

    List<StoreResponseModel> getAllStores();
    StoreResponseModel getStoreById(String storeId);

    StoreResponseModel addStore(StoreRequestModel storeRequestModel);

    StoreResponseModel updateStore(StoreRequestModel storeRequestModel, UUID StoreId);

    void deleteStore(UUID StoreId);
}
