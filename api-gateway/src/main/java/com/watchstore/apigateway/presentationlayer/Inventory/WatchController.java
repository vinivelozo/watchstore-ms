package com.watchstore.apigateway.presentationlayer.Inventory;


import com.watchstore.apigateway.businesslayer.Inventory.WatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/inventories/{inventoryId}/watches")
public class WatchController {

    private WatchService watchService;

    public WatchController(WatchService watchService) {
        this.watchService = watchService;
    }

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WatchResponseModel>> getAllWatches(@PathVariable UUID inventoryId, @RequestParam(required = false) Map<String,String> queryParam) {
        return ResponseEntity.ok().body(watchService.getAllWatches(inventoryId,queryParam));
    }

    @GetMapping(value = "/{referenceNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WatchResponseModel> getWatchById(@PathVariable UUID inventoryId, @PathVariable String referenceNumber ,@RequestParam(required = false)Map<String,String> queryParam){
        return ResponseEntity.ok().body(watchService.getWatchById(inventoryId,referenceNumber));

    }



    @PostMapping()
    public ResponseEntity<WatchResponseModel> addWatch(@PathVariable UUID inventoryId , @RequestBody WatchRequestModel watchRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(watchService.addWatch(inventoryId,watchRequestModel));
    }

    @PutMapping("/{referenceNumber}")
    public ResponseEntity<WatchResponseModel> updateWatch(@PathVariable UUID inventoryId, @PathVariable String referenceNumber ,@RequestBody WatchRequestModelUpdate watchRequestModelUpdate) {
        WatchResponseModel updatedWatch = watchService.updateWatch(inventoryId,watchRequestModelUpdate,referenceNumber);
        return ResponseEntity.ok(updatedWatch);
    }

    @DeleteMapping("/{referenceNumber}")
    public ResponseEntity<Void> deleteWatch(@PathVariable UUID inventoryId, @PathVariable String referenceNumber) {
        watchService.deleteWatch(inventoryId, referenceNumber);
        return ResponseEntity.noContent().build();
    }
}
