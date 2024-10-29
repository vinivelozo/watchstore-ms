package com.watchstore.apigateway.mappinglayer;


import com.watchstore.apigateway.presentationlayer.Inventory.WatchController;
import com.watchstore.apigateway.presentationlayer.Inventory.WatchResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface WatchResponseMapper {

   WatchResponseModel responseModelToResponseModel(WatchResponseModel watchResponseModel);

   List<WatchResponseModel> responseModelListToResponseModelList(List<WatchResponseModel> watchResponseModel);

    @AfterMapping
    default void addLinks(@MappingTarget WatchResponseModel watchResponseModel,
                          @Context UUID inventoryId,
                          @Context String referenceNumber,
                          @Context Map<String, String> queryParam) {

        //Link
        Link allLink = linkTo(methodOn(WatchController.class)
                .getAllWatches(inventoryId, queryParam))
                .withRel("All watches");
        watchResponseModel.add(allLink);

        //Link
        Link selfLink = linkTo(methodOn(WatchController.class)
                .getWatchById(inventoryId, referenceNumber, queryParam))
                .withRel("watch by id");
        watchResponseModel.add(selfLink);
    }
}
