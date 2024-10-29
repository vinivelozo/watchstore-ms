package com.watchstore.apigateway.businesslayer.Sales;



import com.watchstore.apigateway.presentationlayer.Sales.SaleRequestModel;
import com.watchstore.apigateway.presentationlayer.Sales.SaleResponseModel;

import java.util.List;

public interface SaleService {

    List<SaleResponseModel> getAllPurchaseForEmployee(String employeeId);
    SaleResponseModel getEmployeePurchaseBySaleId(String employeeId, String saleId);

    SaleResponseModel addEmployeePurchase(SaleRequestModel saleRequestModel, String employeeId);

    SaleResponseModel updateEmployeeSale(SaleRequestModel saleRequestModel, String employeeId, String saleId);

    void deletePurchaseFromEmployee(String employeeId, String saleId);
}
