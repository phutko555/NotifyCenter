package com.crocobet.notifications.service;

import com.crocobet.notifications.model.address.AddressType;

import com.crocobet.notifications.model.preference.PreferenceType;
import com.crocobet.notifications.repository.AddressTypeRepository;

import com.crocobet.notifications.repository.PreferenceTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressTypeService {

    private AddressTypeRepository addressTypeRepository;
    private PreferenceTypeRepository preferenceTypeRepository;

    @Autowired
    public AddressTypeService(AddressTypeRepository addressTypeRepository, PreferenceTypeRepository preferenceTypeRepository) {
        this.addressTypeRepository = addressTypeRepository;
        this.preferenceTypeRepository = preferenceTypeRepository;

    }

    @Transactional
    public void addAddressType(List<AddressType> addressTypes) {
        List<PreferenceType> preferenceTypes = new ArrayList<>();

        for (AddressType type : addressTypes) {
            if (!addressTypeRepository.existsByAddressTypeName(type.getAddressTypeName())) {
                PreferenceType preferenceType = new PreferenceType();
                preferenceType.setPreferenceTypeName(type.getAddressTypeName());
                preferenceTypes.add(preferenceType);
            }else{
                throw new IllegalArgumentException("მისამართი უკვე არსებობს: " + type.getAddressTypeName());
            }

        }

        addressTypeRepository.saveAll(addressTypes);
        preferenceTypeRepository.saveAll(preferenceTypes);

    }

    public List<AddressType> getAddressTypes(){
        return addressTypeRepository.findAll();
    }
}