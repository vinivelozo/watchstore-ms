package com.watchstore.apigateway.presentationlayer.Sales;


import com.watchstore.apigateway.domainclientlayer.Sales.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class SaleResponseModel extends RepresentationModel<SaleResponseModel> {

    private String saleId;
    private String inventoryId;
    private String referenceNumber;
    private String storeId;
    private String EmployeeFirstName;
    private String EmployeeLastName;
    private String employeeId;

    //private String brand;
    //private String color;
    //private String model;
    private SaleStatus saleStatus;


}
