package com.watchstore.inventory.presentationlayer;

import com.watchstore.inventory.businesslayer.InventoryService;
import org.springframework.http.HttpStatus;
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

    @GetMapping()
    public ResponseEntity<List<InventoryResponseModel>> getAllInventories() {
        return ResponseEntity.ok().body(inventoryService.getAllInventories());
    }

    @GetMapping("{inventoryId}")
    public ResponseEntity<InventoryResponseModel> getInventoryById(@PathVariable String inventoryId){
        return ResponseEntity.ok().body(inventoryService.getInventoryById(inventoryId));

    }

    @PostMapping()
    public ResponseEntity<InventoryResponseModel> addInventory(@RequestBody InventoryRequestModel inventoryRequestModel){
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
