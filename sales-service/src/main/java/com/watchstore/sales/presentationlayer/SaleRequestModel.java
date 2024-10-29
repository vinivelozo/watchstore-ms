package com.watchstore.sales.presentationlayer;

import com.watchstore.sales.dataaccesslayer.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequestModel {

    private String inventoryId;
    private String referenceNumber;
    private String employeeId;
    private String storeId;
    private SaleStatus saleStatus;


}
