package com.watchstore.apigateway.businesslayer.Stores;

import com.watchstore.apigateway.domainclientlayer.Stores.StoresServiceClient;

import com.watchstore.apigateway.mappinglayer.StoreResponseMapper;
import com.watchstore.apigateway.presentationlayer.Stores.StoreRequestModel;
import com.watchstore.apigateway.presentationlayer.Stores.StoreResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class StoreServiceImpl implements StoreService {

    private StoresServiceClient storesServiceClient;
    private StoreResponseMapper storeResponseMapper;


    public StoreServiceImpl(StoresServiceClient storesServiceClient, StoreResponseMapper storeResponseMapper) {
        this.storesServiceClient = storesServiceClient;
        this.storeResponseMapper = storeResponseMapper;
    }

    @Override
    public List<StoreResponseModel> getAllStores() {

        return storeResponseMapper.responseModelListToResponseModelList(storesServiceClient.getAllStores());
    }

    @Override
    public StoreResponseModel getStoreById(String storeId) {
        return storeResponseMapper.responseModelToResponseModel(storesServiceClient.getStoreByStoreId(storeId));
    }

    @Override
    public StoreResponseModel addStore(StoreRequestModel storeRequestModel) {

        return storeResponseMapper.responseModelToResponseModel(storesServiceClient.addStore(storeRequestModel));
    }

    @Override
    public StoreResponseModel updateStore(StoreRequestModel storeRequestModel, UUID storeId) {

        return storeResponseMapper.responseModelToResponseModel(storesServiceClient.updateStore(storeRequestModel, storeId));
    }

    @Override
    public void deleteStore(UUID storeId) {

        storesServiceClient.deleteStore(storeId);
    }

}
