package com.watchstore.stores.dataaccesslayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "stores")
@Data
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private StoreIdentifier storeIdentifier;

    @Embedded
    private Address address;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="store_phonenumbers", joinColumns = @JoinColumn(name = "store_id"))
    private List<PhoneNumber> phoneNumbers;


    private String email;

    public Store(@NotNull Address address, @NotNull List<PhoneNumber> phoneNumbers,@NotNull String email) {
        this.storeIdentifier = new StoreIdentifier();
        this.address = address;
        this.phoneNumbers = phoneNumbers;
        this.email = email;
    }
}
