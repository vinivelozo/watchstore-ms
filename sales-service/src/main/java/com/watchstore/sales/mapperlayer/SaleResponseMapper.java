package com.watchstore.sales.mapperlayer;


import com.watchstore.sales.dataaccesslayer.Sale;
import com.watchstore.sales.presentationlayer.SaleResponseModel;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface SaleResponseMapper {


    @Mapping(expression = "java(sale.getSaleIdentifier().getSaleId())", target = "saleId")
    @Mapping(expression = "java(sale.getInventoryModel().getInventoryId())", target = "inventoryId")
    @Mapping(expression = "java(sale.getWatchModel().getReferenceNumber())", target = "referenceNumber")
    //@Mapping(expression = "java(sale.getInventoryModel().getBrand())", target = "brand")
    //@Mapping(expression = "java(sale.getInventoryModel().getModel())", target = "model")
    //@Mapping(expression = "java(sale.getInventoryModel().getColor())", target = "color")
    @Mapping(expression = "java(sale.getStoreModel().getStoreId())", target = "storeId")
    @Mapping(expression = "java(sale.getEmployeeModel().getEmployeeId())", target = "employeeId")
    @Mapping(expression = "java(sale.getEmployeeModel().getFirstName())", target = "employeeFirstName")
    @Mapping(expression = "java(sale.getEmployeeModel().getLastName())", target = "employeeLastName")



    SaleResponseModel entityToResponseModel(Sale sale);

    List<SaleResponseModel> entityListToResponseModelList(List<Sale> sales);

}

