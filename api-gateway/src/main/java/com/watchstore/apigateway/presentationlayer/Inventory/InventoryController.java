package com.watchstore.apigateway.presentationlayer.Inventory;


import com.watchstore.apigateway.businesslayer.Inventory.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/inventories")
public class InventoryController {

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InventoryResponseModel>> getAllInventories() {
        return ResponseEntity.ok().body(inventoryService.getAllInventories());
    }

    @GetMapping(value = "/{inventoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryResponseModel> getInventoryById(@PathVariable String inventoryId){
        return ResponseEntity.ok().body(inventoryService.getInventoryByInventoryId(inventoryId));

    }

    @PostMapping()
    public ResponseEntity<InventoryResponseModel> addVInventory(@RequestBody InventoryRequestModel inventoryRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.addInventory(inventoryRequestModel));
    }


    @PutMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponseModel> updateInventory(@PathVariable UUID inventoryId,
                                                                  @RequestBody InventoryRequestModel inventoryRequestModel) {
        InventoryResponseModel updatedInventory = inventoryService.updateInventory(inventoryRequestModel, inventoryId);
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable UUID inventoryId) {
        inventoryService.deleteInventory(inventoryId);
        return ResponseEntity.noContent().build();
    }

}
