package com.watchstore.sales.dataaccesslayer;

import com.watchstore.sales.domainclientlayer.employees.EmployeeModel;
import com.watchstore.sales.domainclientlayer.inventory.InventoryModel;
import com.watchstore.sales.domainclientlayer.stores.StoreModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
@ActiveProfiles("test")
class SaleRepositoryIntegrationTest {


    @Autowired
    SaleRepository saleRepository;

    @Test
    public void whenEmployeeIdIsValid_thenReturnSale(){
        var saleIdentifier = new SaleIdentifier();
        var employeeModel = EmployeeModel.builder()
                .employeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")
                //.firstName("Vilma")
                //  .lastName("Chawner")
                .build();

        var storeModel = StoreModel.builder()
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261") //regarder dans le datasql file et mettre les donnes ici. Import lles donnees egalement
                .build();

        var inventoryModel = InventoryModel.builder()

                ///.model("Submariner")
                //.brand("Rolex")
                // .color("Gold")
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var sale1 = Sale.builder()
                .saleIdentifier(saleIdentifier)
                .employeeModel(employeeModel)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        var sale2 = Sale.builder()
                .saleIdentifier(new SaleIdentifier())
                .employeeModel(employeeModel)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        saleRepository.save(sale1);
        saleRepository.save(sale2);

        List<Sale> sales = saleRepository.findSaleByEmployeeModel_EmployeeId(employeeModel.getEmployeeId());

        assertNotNull(sales);
        assertEquals(4, sales.size());

    }

    @Test
    public void whenSaleIdIsInvalid_thenReturnNull(){
        var saleIdentifier = new SaleIdentifier();
        var employeeModel = EmployeeModel.builder()
                .employeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")
                //.firstName("Vilma")
                //  .lastName("Chawner")
                .build();

        var storeModel = StoreModel.builder()
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261") //regarder dans le datasql file et mettre les donnes ici. Import lles donnees egalement
                .build();

        var inventoryModel = InventoryModel.builder()

                ///.model("Submariner")
                //.brand("Rolex")
                // .color("Gold")
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var sale1 = Sale.builder()
                .saleIdentifier(saleIdentifier)
                .employeeModel(employeeModel)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        var sale2 = Sale.builder()
                .saleIdentifier(new SaleIdentifier())
                .employeeModel(employeeModel)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        saleRepository.save(sale1);
        saleRepository.save(sale2);


        Sale sale = saleRepository.findSaleBySaleIdentifier_SaleId("11");

    }




    }

