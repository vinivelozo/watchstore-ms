package com.watchstore.inventory.dataaccesslayer.inventory;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class InventoryIdentifier {

    private String inventoryId;

    public InventoryIdentifier(){
        this.inventoryId = UUID.randomUUID().toString();
    }

    public InventoryIdentifier(UUID inventoryId){this.inventoryId = inventoryId.toString();}
}
