package com.watchstore.sales.dataaccesslayer;


import com.watchstore.sales.domainclientlayer.employees.EmployeeModel;
import com.watchstore.sales.domainclientlayer.inventory.InventoryModel;
import com.watchstore.sales.domainclientlayer.stores.StoreModel;
import com.watchstore.sales.domainclientlayer.watch.WatchModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "sales")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Sale {

    @Id
    private String id;

    @Indexed(unique = true)
    private SaleIdentifier saleIdentifier;

    private EmployeeModel employeeModel;

    private InventoryModel inventoryModel;

    private StoreModel storeModel;

    private WatchModel watchModel;

    private SaleStatus saleStatus;


}
