package com.watchstore.inventory.presentationlayer;

import com.watchstore.inventory.dataaccesslayer.watch.Feature;
import com.watchstore.inventory.dataaccesslayer.watch.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchResponseModel {

    private String referenceNumber;
    private String inventoryId;
    private String brand;
    private String model;
    private String color;
    private Integer year;
    private Status status;
    List<Feature> features;
}
