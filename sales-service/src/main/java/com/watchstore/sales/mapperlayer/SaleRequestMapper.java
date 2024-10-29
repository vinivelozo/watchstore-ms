package com.watchstore.sales.mapperlayer;


import com.watchstore.sales.dataaccesslayer.Sale;
import com.watchstore.sales.dataaccesslayer.SaleIdentifier;
import com.watchstore.sales.domainclientlayer.employees.EmployeeModel;
import com.watchstore.sales.domainclientlayer.inventory.InventoryModel;
import com.watchstore.sales.domainclientlayer.stores.StoreModel;
import com.watchstore.sales.domainclientlayer.watch.WatchModel;
import com.watchstore.sales.presentationlayer.SaleRequestModel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleRequestMapper {
    @Mapping(target = "id", ignore = true)
    Sale requestModelToEntity(SaleRequestModel saleRequestModel,
                              SaleIdentifier saleIdentifier,
                              EmployeeModel employeeModel,
                              InventoryModel inventoryModel,
                              StoreModel storeModel,
                              WatchModel watchModel

                              );
}
