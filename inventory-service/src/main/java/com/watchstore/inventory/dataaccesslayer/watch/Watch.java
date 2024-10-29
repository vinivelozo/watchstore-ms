package com.watchstore.inventory.dataaccesslayer.watch;

import com.watchstore.inventory.dataaccesslayer.inventory.InventoryIdentifier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
@Table(name="watches")
@Getter
@Data
public class Watch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private WatchIdentifier watchIdentifier;

    @Embedded
    private InventoryIdentifier inventoryIdentifier;

//    @Embedded
//    private Feature feature;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "watch_features", joinColumns = @JoinColumn(name= "reference_number"))
    private List<Feature> features;

    private String brand;
    private String model;
    private String color;
    @Column(name = "\"year\"")
    private Integer year;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Watch(@NotNull List<Feature> features,@NotNull String brand,@NotNull String model,@NotNull String color,@NotNull Integer year,@NotNull Status status) {
        this.watchIdentifier = new WatchIdentifier();
        this.features = features;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.year = year;
        this.status = status;
    }
}
