package com.watchstore.apigateway.presentationlayer.Sales;


import com.watchstore.apigateway.domainclientlayer.Sales.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequestModel {

    private String inventoryId;
    private String referenceNumber;
    private String employeeId;
//    private String firstName;
//    private String lastName;
    private String storeId;
    private SaleStatus saleStatus;


}
