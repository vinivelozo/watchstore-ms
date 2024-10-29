package com.watchstore.sales.businesslayer;

import com.watchstore.sales.presentationlayer.SaleRequestModel;
import com.watchstore.sales.presentationlayer.SaleResponseModel;

import java.util.List;
import java.util.UUID;

public interface SaleService {

    List<SaleResponseModel> getAllPurchaseForEmployee(String employeeId);
    SaleResponseModel getEmployeePurchaseBySaleId(String employeeId, String saleId);

    SaleResponseModel addEmployeePurchase(SaleRequestModel saleRequestModel, String employeeId);

    SaleResponseModel updateEmployeeSale(SaleRequestModel saleRequestModel, String employeeId, String saleId);

    void deletePurchaseFromEmployee(String employeeId, String saleId);
}
