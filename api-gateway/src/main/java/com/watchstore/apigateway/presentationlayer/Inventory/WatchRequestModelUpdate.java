package com.watchstore.apigateway.presentationlayer.Inventory;


import com.watchstore.apigateway.domainclientlayer.Inventory.Feature;
import com.watchstore.apigateway.domainclientlayer.Inventory.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WatchRequestModelUpdate {

    private String brand;
    private String model;
    private String color;
    private Integer year;
    private Status status;
    List<Feature> features;
}
