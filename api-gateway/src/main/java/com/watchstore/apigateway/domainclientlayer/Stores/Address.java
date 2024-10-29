package com.watchstore.apigateway.domainclientlayer.Stores;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class Address {

    private String street;
    private String city;
    private String province;
    private String country;
    private String postal_code;

    public Address(@NotNull String street, @NotNull String city, @NotNull String province, @NotNull String country, @NotNull String postal_code) {
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postal_code = postal_code;
    }
}