package com.watchstore.inventory.businesslayer;

import com.watchstore.inventory.dataaccesslayer.inventory.InventoryIdentifier;
import com.watchstore.inventory.dataaccesslayer.watch.Status;
import com.watchstore.inventory.dataaccesslayer.watch.Watch;
import com.watchstore.inventory.dataaccesslayer.watch.WatchIdentifier;
import com.watchstore.inventory.dataaccesslayer.watch.WatchRepository;
import com.watchstore.inventory.mapperlayer.watch.WatchRequestMapper;
import com.watchstore.inventory.mapperlayer.watch.WatchResponseMapper;
import com.watchstore.inventory.presentationlayer.WatchRequestModel;
import com.watchstore.inventory.presentationlayer.WatchRequestModelUpdate;
import com.watchstore.inventory.presentationlayer.WatchResponseModel;
import com.watchstore.inventory.utils.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class WatchServiceImpl implements WatchService{

    private WatchRepository watchRepository;

    private WatchResponseMapper watchResponseMapper;

    private WatchRequestMapper watchRequestMapper;

    public WatchServiceImpl(WatchRepository watchRepository, WatchResponseMapper watchResponseMapper, WatchRequestMapper watchRequestMapper) {
        this.watchRepository = watchRepository;
        this.watchResponseMapper = watchResponseMapper;
        this.watchRequestMapper = watchRequestMapper;
    }

    @Override
    public List<WatchResponseModel> getAllWatches(UUID inventoryId, Map<String, String> queryParam){

        String status = queryParam.get("status");
        //String usageType = queryParam.get("type");

        Map<String, Status> statusMap = new HashMap<>();

        statusMap.put("available", Status.AVAILABLE);
        statusMap.put("sold",Status.SOLD);

//        Map<String, UsageType> usageTypeMap = new HashMap<>();
//        usageTypeMap.put("new", UsageType.NEW);
//        usageTypeMap.put("used", UsageType.USED);

//        if(status != null & usageType != null){
//            List<Vehicle>vehicleList = vehicleRepository.findAllByInventoryIdentifier_InventoryIdAndStatusEqualsAndUsageTypeEquals(inventoryId.toString(),statusMap.get(status.toLowerCase()),usageTypeMap.get(usageType.toLowerCase()));
//            return vehicleResponseMapper.entityListToResponseModelList(vehicleList);
//        }

//        if(usageType != null){
//            List<Vehicle>vehicleList = vehicleRepository.findAllByInventoryIdentifier_InventoryIdAndUsageTypeEquals(inventoryId.toString(),usageTypeMap.get(usageType.toLowerCase()));
//            return vehicleResponseMapper.entityListToResponseModelList(vehicleList);
//        }

        if(status !=null){
            List<Watch>watchList = watchRepository.findAllByInventoryIdentifier_InventoryIdAndStatusEquals(inventoryId.toString(),statusMap.get(status.toLowerCase()));
            return watchResponseMapper.entityListToResponseModelList(watchList);
        }
        else
            return watchResponseMapper.entityListToResponseModelList(watchRepository.findAllByInventoryIdentifier_InventoryId(inventoryId.toString()));
    }
    @Override
    public WatchResponseModel getWatchByInventoryIdAndByReferenceNumber(UUID inventoryId, String referenceNumber) {

        Watch watch = watchRepository.findByInventoryIdentifier_InventoryIdAndWatchIdentifier_ReferenceNumber(inventoryId.toString(),referenceNumber);
        if(watch == null){
            throw new NotFoundException("No watch found with ID: " + referenceNumber+ " and inventory Id: "+inventoryId);
        }
        return watchResponseMapper.entityToResponseModel(watch);

    }

    @Override
    public WatchResponseModel addWatch(UUID inventoryId, WatchRequestModel watchRequestModel){

        Watch watch = watchRequestMapper.requestModelToEntity(watchRequestModel,new WatchIdentifier(watchRequestModel.getReferenceNumber()), new InventoryIdentifier(inventoryId) );
        return watchResponseMapper.entityToResponseModel(watchRepository.save(watch));

    }

    @Override
    public WatchResponseModel updateWatch(UUID inventoryId, WatchRequestModelUpdate watchRequestModelUpdate, String referenceNumber) {
        Watch existingWatch = watchRepository.findByInventoryIdentifier_InventoryIdAndWatchIdentifier_ReferenceNumber(inventoryId.toString(),referenceNumber);
        if(existingWatch == null){
            throw new NotFoundException("No watch found with ID: " + referenceNumber);
        }
        Watch watch = watchRequestMapper.requestModelUpdateToEntity(watchRequestModelUpdate, existingWatch.getWatchIdentifier(), new InventoryIdentifier(inventoryId));
        watch.setId(existingWatch.getId());

        return watchResponseMapper.entityToResponseModel(watchRepository.save(watch));


    }

    @Override
    public void deleteWatch(UUID inventoryId, String referenceNumber) {

        Watch watch = watchRepository.findByInventoryIdentifier_InventoryIdAndWatchIdentifier_ReferenceNumber(inventoryId.toString()
                ,referenceNumber);
        if(watch == null){
            throw new NotFoundException("No watch found with ID: " + referenceNumber);
        }

        watch.setStatus(Status.valueOf("SOLD"));
        watchRepository.save(watch);

        watchRepository.delete(watch);

    }
}
