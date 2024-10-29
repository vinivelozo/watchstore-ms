package com.watchstore.stores.presentationlayer;

import com.watchstore.stores.dataaccesslayer.Address;
import com.watchstore.stores.dataaccesslayer.PhoneNumber;
import lombok.*;
//import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreResponseModel  {

    //private Integer id;
    private String storeId;
    //private StoreIdentifier storeIdentifier;
    private Address address;
    private String email;
    List<PhoneNumber> phoneNumbers;
}
