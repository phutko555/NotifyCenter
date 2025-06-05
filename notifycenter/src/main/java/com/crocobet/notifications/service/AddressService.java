package com.crocobet.notifications.service;

import com.crocobet.notifications.model.address.Address;

import com.crocobet.notifications.model.preference.Preference;
import com.crocobet.notifications.repository.AddressRepository;

import com.crocobet.notifications.repository.PreferenceRepository;
import com.crocobet.notifications.repository.PreferenceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AddressService {

    private AddressRepository addressRepository;
    private PreferenceRepository preferenceRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository, PreferenceRepository preferenceRepository) {
        this.addressRepository = addressRepository;
        this.preferenceRepository = preferenceRepository;
    }


    public void removeAddress(Long addressId){
        Address address = addressRepository.findById(addressId).orElseThrow();
        Preference preference = preferenceRepository.findByPreferenceTypeName(address.getAddressType().getAddressTypeName());

        preferenceRepository.delete(preference);
        addressRepository.delete(address);

    }

    public List<Address> addresses(){
      return addressRepository.findAll();
    }
}
