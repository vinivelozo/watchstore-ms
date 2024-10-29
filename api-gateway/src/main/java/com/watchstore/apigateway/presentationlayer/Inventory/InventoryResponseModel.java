package com.watchstore.apigateway.presentationlayer.Inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
//import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class InventoryResponseModel extends RepresentationModel<InventoryResponseModel> {

    private String inventoryId;
    private String type;
}
