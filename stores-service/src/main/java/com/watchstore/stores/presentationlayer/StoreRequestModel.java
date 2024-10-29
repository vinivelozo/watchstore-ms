package com.watchstore.stores.presentationlayer;

import com.watchstore.stores.dataaccesslayer.Address;
import com.watchstore.stores.dataaccesslayer.PhoneNumber;
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
