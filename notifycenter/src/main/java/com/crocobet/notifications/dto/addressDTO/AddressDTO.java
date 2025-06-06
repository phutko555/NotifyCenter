package com.crocobet.notifications.dto.addressDTO;

import com.crocobet.notifications.model.address.AddressType;

public class AddressDTO {

    private Long addressId;
    private AddressType addressType;

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
