package com.watchstore.sales.presentationlayer;

import com.watchstore.sales.dataaccesslayer.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponseModel  {

    private String saleId;
    private String inventoryId;
    private String referenceNumber;
    private String storeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String employeeId;

    //private String brand;
    //private String color;
    //private String model;
    private SaleStatus saleStatus;


}
