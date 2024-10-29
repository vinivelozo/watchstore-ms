package com.watchstore.apigateway.mappinglayer;

import com.watchstore.apigateway.presentationlayer.Inventory.InventoryController;
import com.watchstore.apigateway.presentationlayer.Inventory.InventoryResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface InventoryResponseMapper {

    InventoryResponseModel responseModelToResponseModel(InventoryResponseModel inventoryResponseModel);

    List<InventoryResponseModel> responseModelListToResponseModelList(List<InventoryResponseModel> inventoryResponseModel);

    @AfterMapping
    default void addLinks(@MappingTarget InventoryResponseModel inventoryResponseModel) {

        //Link
        Link allLink = linkTo(methodOn(InventoryController.class)
                .getAllInventories())
                .withRel("All inventories");
        inventoryResponseModel.add(allLink);

        //Link
        Link selfLink = linkTo(methodOn(InventoryController.class)
                .getInventoryById(inventoryResponseModel.getInventoryId()))
                .withRel("inventory by id");
        inventoryResponseModel.add(selfLink);
    }
}
