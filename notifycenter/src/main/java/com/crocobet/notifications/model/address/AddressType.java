package com.crocobet.notifications.model.address;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="addressTypes")
public class AddressType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressTypeId;

    private String addressTypeName;

    @OneToMany(mappedBy = "addressType")
    private List<Address> address;


    public Long getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(Long addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    public String getAddressTypeName() {
        return addressTypeName;
    }

    public void setAddressTypeName(String addressTypeName) {
        this.addressTypeName = addressTypeName;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }
}
