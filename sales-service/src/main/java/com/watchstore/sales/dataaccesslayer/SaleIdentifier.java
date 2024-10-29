package com.watchstore.sales.dataaccesslayer;



import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter

public class SaleIdentifier {

    private String saleId;

    public SaleIdentifier() {
        this.saleId = UUID.randomUUID().toString();
    }
}
