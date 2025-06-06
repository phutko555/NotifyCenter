package com.crocobet.notifications.dto.addressDTO;

import com.crocobet.notifications.model.address.Address;

import com.crocobet.notifications.model.preference.Preference;

public class AddressWithPreferenceDTO {
    private Address address;

    private Preference preference;


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public AddressWithPreferenceDTO(Address address, Preference preference) {
        this.address = address;
        this.preference = preference;
    }
}
