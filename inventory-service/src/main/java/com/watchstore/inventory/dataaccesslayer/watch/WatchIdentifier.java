package com.watchstore.inventory.dataaccesslayer.watch;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class WatchIdentifier {

    private String referenceNumber;

    public WatchIdentifier(){

        this.referenceNumber = UUID.randomUUID().toString();
    }

    public WatchIdentifier(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
