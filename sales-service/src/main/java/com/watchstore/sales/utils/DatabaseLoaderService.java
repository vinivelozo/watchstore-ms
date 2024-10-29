package com.watchstore.sales.utils;

import com.watchstore.sales.dataaccesslayer.*;
import com.watchstore.sales.domainclientlayer.inventory.Status;
import com.watchstore.sales.domainclientlayer.stores.StoreModel;
import com.watchstore.sales.domainclientlayer.employees.EmployeeModel;
import com.watchstore.sales.domainclientlayer.inventory.InventoryModel;
import com.watchstore.sales.dataaccesslayer.SaleIdentifier;
import com.watchstore.sales.dataaccesslayer.SaleRepository;
import com.watchstore.sales.domainclientlayer.watch.WatchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoaderService implements CommandLineRunner {

    @Autowired
    SaleRepository saleRepository;

    @Override
    public void run(String ... args) throws Exception {


        var saleIdentifier = new SaleIdentifier();
        saleIdentifier.setSaleId("11");


        var inventoryModel = InventoryModel.builder()

                ///.model("Submariner")
                //.brand("Rolex")
               // .color("Gold")
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var watchModel = WatchModel.builder()
                .referenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234")
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();


        var storeModel = StoreModel.builder()
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261") //regarder dans le datasql file et mettre les donnes ici. Import lles donnees egalement
                .build();

        var employeeModel = EmployeeModel.builder()
                .employeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")
                .firstName("Vini")
                .lastName("Borges")
                .build();


        var sale = Sale.builder()
                .saleIdentifier(saleIdentifier)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .employeeModel(employeeModel)
                .watchModel(watchModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        saleRepository.save(sale);

    }


}
