package com.watchstore.apigateway.domainclientlayer.Inventory;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class Feature {

    private String bracelet;
    private Integer diameter;
    private String batteryType;
    private Double price;

    public Feature(@NotNull String bracelet, @NotNull Integer diameter, @NotNull String batteryType, @NotNull Double price) {
        this.bracelet = bracelet;
        this.diameter = diameter;
        this.batteryType = batteryType;
        this.price = price;
    }
}
