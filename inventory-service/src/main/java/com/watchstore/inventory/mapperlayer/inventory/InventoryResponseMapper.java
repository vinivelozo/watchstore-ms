package com.watchstore.inventory.mapperlayer.inventory;

import com.watchstore.inventory.dataaccesslayer.inventory.Inventory;
import com.watchstore.inventory.presentationlayer.InventoryController;
import com.watchstore.inventory.presentationlayer.InventoryResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
//import org.springframework.hateoas.Link;

import java.util.List;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface InventoryResponseMapper {

    @Mapping(expression = "java(inventory.getInventoryIdentifier().getInventoryId())", target = "inventoryId")
    InventoryResponseModel entityToResponseModel(Inventory inventory);

    List<InventoryResponseModel> entityListToResponseModelList(List<Inventory> inventoryList);

//    @AfterMapping
//    default void addLinks(@MappingTarget InventoryResponseModel model, Inventory inventory){
//        // link inventory by id
//        Link selfLink = linkTo(methodOn(InventoryController.class)
//                .getInventoryById(model.getInventoryId()))
//                .withRel("Inventory By id");
//        model.add(selfLink);
//
//        // all inventories
//        Link inventoriesLink =
//                linkTo(methodOn(InventoryController.class)
//                        .getAllInventories())
//                        .withRel("All inventories");
//        model.add(inventoriesLink);
//    }
}
