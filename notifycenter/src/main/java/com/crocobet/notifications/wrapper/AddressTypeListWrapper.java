package com.crocobet.notifications.wrapper;

import com.crocobet.notifications.model.address.AddressType;

import java.util.List;

public class AddressTypeListWrapper {
    private List<AddressType> addressTypes;

    public List<AddressType> getAddressTypes() {
        return addressTypes;
    }

    public void setAddressTypes(List<AddressType> addressTypes) {
        this.addressTypes = addressTypes;
    }
}