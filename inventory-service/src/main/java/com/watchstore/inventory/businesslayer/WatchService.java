package com.watchstore.inventory.businesslayer;

import com.watchstore.inventory.presentationlayer.WatchRequestModel;
import com.watchstore.inventory.presentationlayer.WatchRequestModelUpdate;
import com.watchstore.inventory.presentationlayer.WatchResponseModel;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WatchService {

    List<WatchResponseModel> getAllWatches(UUID inventoryId, Map<String, String> queryParams);
    WatchResponseModel getWatchByInventoryIdAndByReferenceNumber(UUID inventoryId, String referenceNumber);

    WatchResponseModel addWatch(UUID inventoryId, WatchRequestModel watchRequestModel);

    WatchResponseModel updateWatch(UUID inventoryId, WatchRequestModelUpdate watchRequestModelupdate, String referenceNumber);

    void deleteWatch(UUID inventoryId, String referenceNumber);
}
