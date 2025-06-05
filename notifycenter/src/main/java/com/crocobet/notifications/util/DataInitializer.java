package com.crocobet.notifications.util;

import com.crocobet.notifications.model.User;

import com.crocobet.notifications.model.address.Address;
import com.crocobet.notifications.model.address.AddressType;
import com.crocobet.notifications.model.enums.Role;

import com.crocobet.notifications.model.preference.PreferenceType;
import com.crocobet.notifications.repository.AddressTypeRepository;
import com.crocobet.notifications.repository.PreferenceTypeRepository;
import com.crocobet.notifications.repository.UserRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private AddressTypeRepository addressTypeRepository;

    private PreferenceTypeRepository preferenceTypeRepository;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, AddressTypeRepository addressTypeRepository, PreferenceTypeRepository preferenceTypeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressTypeRepository = addressTypeRepository;
        this.preferenceTypeRepository = preferenceTypeRepository;
    }

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("notifier").isEmpty()) {
            User systemUser = new User();
            systemUser.setUsername("notifier");
            systemUser.setPassword(passwordEncoder.encode("system2004"));
            systemUser.setRole(Role.SYSTEM);
            userRepository.save(systemUser);
        }
    }
    @PostConstruct
    public void initAddresses() {
        if (addressTypeRepository.count() == 0 && preferenceTypeRepository.count() == 0) {
            List<AddressType> addressTypes = new ArrayList<>();
            AddressType addressType = new AddressType();
            addressType.setAddressTypeName("SMS");
            addressTypes.add(addressType);

            AddressType addressType1 = new AddressType();
            addressType1.setAddressTypeName("email");
            addressTypes.add(addressType1);

            List<PreferenceType> preferenceTypes = new ArrayList<>();
            PreferenceType preferenceType = new PreferenceType();
            preferenceType.setPreferenceTypeName(addressType.getAddressTypeName());
            preferenceTypes.add(preferenceType);

            PreferenceType preferenceType1 = new PreferenceType();
            preferenceType1.setPreferenceTypeName(addressType1.getAddressTypeName());
            preferenceTypes.add(preferenceType1);

            addressTypeRepository.saveAll(addressTypes);
            preferenceTypeRepository.saveAll(preferenceTypes);
        }
    }

    }

