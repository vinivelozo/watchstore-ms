package com.watchstore.sales.domainclientlayer.watch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.watchstore.sales.domainclientlayer.inventory.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchModel {

    String referenceNumber;
    String inventoryId;
    String brand;
    String model;
    String color;
    Integer year;
    Status status;
}
