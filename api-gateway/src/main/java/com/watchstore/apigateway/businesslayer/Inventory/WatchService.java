package com.watchstore.apigateway.businesslayer.Inventory;



import com.watchstore.apigateway.presentationlayer.Inventory.WatchRequestModel;
import com.watchstore.apigateway.presentationlayer.Inventory.WatchRequestModelUpdate;
import com.watchstore.apigateway.presentationlayer.Inventory.WatchResponseModel;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WatchService {

    List<WatchResponseModel> getAllWatches(UUID inventoryId, Map<String, String> queryParams);
    WatchResponseModel getWatchById(UUID inventoryId, String referenceNumber);


    WatchResponseModel addWatch(UUID inventoryId, WatchRequestModel watchRequestModel);

    WatchResponseModel updateWatch(UUID inventoryId, WatchRequestModelUpdate watchRequestModelupdate, String referenceNumber);

    void deleteWatch(UUID inventoryId, String referenceNumber);
}
