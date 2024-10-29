package com.watchstore.apigateway.businesslayer.Stores;



import com.watchstore.apigateway.presentationlayer.Stores.StoreRequestModel;
import com.watchstore.apigateway.presentationlayer.Stores.StoreResponseModel;

import java.util.List;
import java.util.UUID;

public interface StoreService {

    List<StoreResponseModel> getAllStores();
    StoreResponseModel getStoreById(String storeId);

    StoreResponseModel addStore(StoreRequestModel storeRequestModel);

    StoreResponseModel updateStore(StoreRequestModel storeRequestModel, UUID storeId);

    void deleteStore(UUID storeId);
}
