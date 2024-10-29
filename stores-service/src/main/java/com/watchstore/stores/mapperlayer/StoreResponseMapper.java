package com.watchstore.stores.mapperlayer;

import com.watchstore.stores.dataaccesslayer.Store;
import com.watchstore.stores.presentationlayer.StoreController;
import com.watchstore.stores.presentationlayer.StoreResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
//import org.springframework.hateoas.Link;

import java.util.List;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface StoreResponseMapper {

    @Mapping(expression = "java(store.getStoreIdentifier().getStoreId())", target ="storeId")
    StoreResponseModel entityToResponseModel(Store store);

    List<StoreResponseModel> entityListToResponseModelList(List<Store> storeList);

//    @AfterMapping
//    default void addLinks(@MappingTarget StoreResponseModel model, Store store){
//        // Stores by id
//        Link selfLink = linkTo(methodOn(StoreController.class)
//                .getStoreById(model.getStoreId()))
//                .withRel("Stores by id");
//        model.add(selfLink);
//
//        Link storesLink =
//                linkTo(methodOn(StoreController.class)
//                        .getAllStores())
//                        .withRel("All stores");
//        model.add(storesLink);
//    }
}
