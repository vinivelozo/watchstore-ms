package com.watchstore.inventory.dataaccesslayer.inventory;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name="inventories")
@Getter
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private InventoryIdentifier inventoryIdentifier;

    private String type;


    public Inventory(@NotNull String type) {
        this.inventoryIdentifier = new InventoryIdentifier();
        this.type = type;
    }
}
