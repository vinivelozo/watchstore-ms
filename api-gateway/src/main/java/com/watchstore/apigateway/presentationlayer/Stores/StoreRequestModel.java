package com.watchstore.apigateway.presentationlayer.Stores;


import com.watchstore.apigateway.domainclientlayer.Stores.Address;
import com.watchstore.apigateway.domainclientlayer.Stores.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequestModel {

    private Address address;
    private String email;
    List<PhoneNumber> phoneNumbers;
}
