package com.watchstore.apigateway.presentationlayer.Inventory;


import com.watchstore.apigateway.domainclientlayer.Inventory.Feature;
import com.watchstore.apigateway.domainclientlayer.Inventory.Status;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
public class WatchResponseModel extends RepresentationModel<WatchResponseModel> {

    private String referenceNumber;
    private String inventoryId;
    private String brand;
    private String model;
    private String color;
    private Integer year;
    private Status status;
    List<Feature> features;
}
