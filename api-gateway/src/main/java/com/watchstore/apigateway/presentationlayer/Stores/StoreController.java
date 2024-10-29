package com.watchstore.apigateway.presentationlayer.Stores;


import com.watchstore.apigateway.businesslayer.Stores.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StoreResponseModel>> getAllStores() {
        return ResponseEntity.ok().body(storeService.getAllStores());
    }

    @GetMapping(value = "/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StoreResponseModel> getStoreById(@PathVariable String storeId){
        return ResponseEntity.ok().body(storeService.getStoreById(storeId));

    }

    @PostMapping()
    public ResponseEntity<StoreResponseModel> addStore(@RequestBody StoreRequestModel storeRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.addStore(storeRequestModel));
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<StoreResponseModel> updateStore(@PathVariable UUID storeId,
                                                                    @RequestBody StoreRequestModel storeRequestModel) {
        StoreResponseModel updatedStore = storeService.updateStore(storeRequestModel, storeId);
        return ResponseEntity.ok(updatedStore);
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable UUID storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.noContent().build();
    }


}
