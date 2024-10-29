package com.watchstore.apigateway.presentationlayer.Stores;


import com.watchstore.apigateway.domainclientlayer.Stores.Address;
import com.watchstore.apigateway.domainclientlayer.Stores.PhoneNumber;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StoreResponseModel extends RepresentationModel<StoreResponseModel> {

    //private Integer id;
    private String storeId;
    //private StoreIdentifier storeIdentifier;
    private Address address;
    private String email;
    List<PhoneNumber> phoneNumbers;
}
