package com.watchstore.apigateway.mappinglayer;



import com.watchstore.apigateway.presentationlayer.Sales.EmployeePurchaseController;
import com.watchstore.apigateway.presentationlayer.Sales.SaleRequestModel;
import com.watchstore.apigateway.presentationlayer.Sales.SaleResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface SaleResponseMapper {


    SaleResponseModel responseModelToResponseModel(SaleResponseModel saleResponseModel);

    List<SaleResponseModel> responseModelListToResponseModelList(List<SaleResponseModel> saleResponseModel);

    @AfterMapping
    default void addLinks(@MappingTarget SaleResponseModel saleResponseModel) {

        //Link
        Link allLink = linkTo(methodOn(EmployeePurchaseController.class)
                .getAllPurchaseForEmployee(saleResponseModel.getEmployeeId()))
                .withRel("All sales");
        saleResponseModel.add(allLink);

        //Link
        Link selfLink = linkTo(methodOn(EmployeePurchaseController.class)
                .getEmployeePurchaseBySaleId(saleResponseModel.getEmployeeId(), saleResponseModel.getSaleId()))
                .withRel("sale by id");
        saleResponseModel.add(selfLink);
    }

}

