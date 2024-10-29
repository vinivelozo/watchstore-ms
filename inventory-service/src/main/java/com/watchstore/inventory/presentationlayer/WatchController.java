package com.watchstore.inventory.presentationlayer;

import com.watchstore.inventory.businesslayer.WatchService;
import org.springframework.http.HttpStatus;
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

    @GetMapping()
    public ResponseEntity<List<WatchResponseModel>> getAllWatches(@PathVariable UUID inventoryId, @RequestParam(required = false) Map<String,String> queryParam) {
        return ResponseEntity.ok().body(watchService.getAllWatches(inventoryId,queryParam));
    }

    @GetMapping("{referenceNumber}")
    public ResponseEntity<WatchResponseModel> getWatchByInventoryIdAndByReferenceNumber(@PathVariable UUID inventoryId, @PathVariable String referenceNumber ,@RequestParam(required = false)Map<String,String> queryParam){
        return ResponseEntity.ok().body(watchService.getWatchByInventoryIdAndByReferenceNumber(inventoryId,referenceNumber));

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
