package com.watchstore.stores.businesslayer;

import com.watchstore.stores.dataaccesslayer.Store;
import com.watchstore.stores.dataaccesslayer.StoreIdentifier;
import com.watchstore.stores.dataaccesslayer.StoreRepository;
import com.watchstore.stores.mapperlayer.StoreRequestMapper;
import com.watchstore.stores.mapperlayer.StoreResponseMapper;
import com.watchstore.stores.presentationlayer.StoreRequestModel;
import com.watchstore.stores.presentationlayer.StoreResponseModel;
import com.watchstore.stores.utils.exceptions.InvalidInputException;
import com.watchstore.stores.utils.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class StoreServiceImpl implements StoreService{

    private StoreRepository storeRepository;
    private StoreResponseMapper storeResponseMapper;
    private StoreRequestMapper storeRequestMapper;

    public StoreServiceImpl(StoreRepository storeRepository,
                            StoreResponseMapper storeResponseMapper,
                            StoreRequestMapper storeRequestMapper) {
        this.storeRepository = storeRepository;
        this.storeResponseMapper = storeResponseMapper;
        this.storeRequestMapper = storeRequestMapper;
    }

    @Override
    public List<StoreResponseModel> getAllStores(){
        List<Store> storeList = storeRepository.findAll();
        return storeResponseMapper.entityListToResponseModelList(storeList);
    }

    @Override
    public StoreResponseModel getStoreById(String storeId){
        Store foundStore = storeRepository.findStoreByStoreIdentifier_StoreId(storeId);
        if(foundStore == null){
            throw new NotFoundException("Store id invalid" + storeId);
        }
        return storeResponseMapper.entityToResponseModel(foundStore);
    }

    @Override
    public StoreResponseModel addStore(StoreRequestModel storeRequestModel){

        Store store = storeRequestMapper.requestModelToEntity(storeRequestModel,
                new StoreIdentifier());


        return storeResponseMapper.entityToResponseModel(storeRepository.save(store));
    }

    @Override
    public StoreResponseModel updateStore(StoreRequestModel storeRequestModel, UUID StoreId) {
        Store existingStore = storeRepository.findStoreByStoreIdentifier_StoreId(StoreId.toString());
        if (existingStore == null){
            throw new NotFoundException("Cannot update id: " + StoreId);
        }
//

        Store store = storeRequestMapper.requestModelToEntity(storeRequestModel,
                existingStore.getStoreIdentifier());
        store.setId(existingStore.getId());


        return storeResponseMapper.entityToResponseModel(storeRepository.save(store));
    }

    @Override
    public void deleteStore(UUID StoreId) {
        Store store = storeRepository.findStoreByStoreIdentifier_StoreId(StoreId.toString());
        if (store == null) {
            throw new NotFoundException("No store found with ID: " + StoreId);
        }
        storeRepository.delete(store);
    }
}
