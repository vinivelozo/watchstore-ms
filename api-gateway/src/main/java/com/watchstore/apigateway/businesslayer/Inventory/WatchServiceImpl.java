package com.watchstore.apigateway.businesslayer.Inventory;


import com.watchstore.apigateway.domainclientlayer.Inventory.Status;
import com.watchstore.apigateway.domainclientlayer.Inventory.WatchServiceClient;
import com.watchstore.apigateway.mappinglayer.WatchResponseMapper;
import com.watchstore.apigateway.presentationlayer.Inventory.WatchRequestModel;
import com.watchstore.apigateway.presentationlayer.Inventory.WatchRequestModelUpdate;
import com.watchstore.apigateway.presentationlayer.Inventory.WatchResponseModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class WatchServiceImpl implements WatchService {

    private final WatchServiceClient watchServiceClient;

    private WatchResponseMapper watchResponseMapper;


    public WatchServiceImpl(WatchServiceClient watchServiceClient, WatchResponseMapper watchResponseMapper) {
        this.watchServiceClient = watchServiceClient;
        this.watchResponseMapper = watchResponseMapper;
    }

    @Override
    public List<WatchResponseModel> getAllWatches(UUID inventoryId, Map<String, String> queryParam){

//        String status = queryParam.get("status");
//        //String usageType = queryParam.get("type");
//
//        Map<String, Status> statusMap = new HashMap<>();
//
//        statusMap.put("available", Status.AVAILABLE);
//        statusMap.put("sold",Status.SOLD);
        return watchResponseMapper.responseModelListToResponseModelList(watchServiceClient.getAllWatches(inventoryId));

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

//        if(status !=null){
//            List<Watch>watchList = watchRepository.findAllByInventoryIdentifier_InventoryIdAndStatusEquals(inventoryId.toString(),statusMap.get(status.toLowerCase()));
//            return watchResponseMapper.entityListToResponseModelList(watchList);
//        }
//        else
//            return watchResponseMapper.entityListToResponseModelList(watchRepository.findAllByInventoryIdentifier_InventoryId(inventoryId.toString()));
    }
    @Override
    public WatchResponseModel getWatchById(UUID inventoryId, String referenceNumber) {


        return watchResponseMapper.responseModelToResponseModel(watchServiceClient.getWatchByInventoryIdAndByReferenceNumber(inventoryId, referenceNumber));



    }

    @Override
    public WatchResponseModel addWatch(UUID inventoryId, WatchRequestModel watchRequestModel){

        return watchResponseMapper.responseModelToResponseModel(watchServiceClient.addWatch(inventoryId, watchRequestModel));

    }

    @Override
    public WatchResponseModel updateWatch(UUID inventoryId, WatchRequestModelUpdate watchRequestModelUpdate, String referenceNumber) {

        return watchResponseMapper.responseModelToResponseModel(watchServiceClient.updateWatch(watchRequestModelUpdate, inventoryId, referenceNumber));


    }

    @Override
    public void deleteWatch(UUID inventoryId, String referenceNumber) {

     watchServiceClient.deleteWatch(inventoryId, referenceNumber);

    }
}
