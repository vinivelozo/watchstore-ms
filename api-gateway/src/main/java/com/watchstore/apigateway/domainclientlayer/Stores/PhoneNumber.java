package com.watchstore.apigateway.domainclientlayer.Stores;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class PhoneNumber {


    private PhoneType type;
    private String Number;

    public PhoneNumber(@NotNull PhoneType type, @NotNull String number) {
        this.type = type;
        Number = number;
    }
}
