package com.watchstore.apigateway.mappinglayer;

import com.watchstore.apigateway.presentationlayer.Stores.StoreController;
import com.watchstore.apigateway.presentationlayer.Stores.StoreResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface StoreResponseMapper {

    StoreResponseModel responseModelToResponseModel(StoreResponseModel storeResponseModel);

    List<StoreResponseModel> responseModelListToResponseModelList(List<StoreResponseModel> storeResponseModel);

    @AfterMapping
    default void addLinks(@MappingTarget StoreResponseModel storeResponseModel) {

        //Link
        Link allLink = linkTo(methodOn(StoreController.class)
                .getAllStores())
                .withRel("All stores");
        storeResponseModel.add(allLink);

        //Link
        Link selfLink = linkTo(methodOn(StoreController.class)
                .getStoreById(storeResponseModel.getStoreId()))
                .withRel("store by id");
        storeResponseModel.add(selfLink);
    }
}
