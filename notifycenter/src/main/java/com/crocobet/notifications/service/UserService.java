package com.crocobet.notifications.service;

import com.crocobet.notifications.dto.addressDTO.AddressDTO;
import com.crocobet.notifications.dto.adminDTO.UserRegistrationRequest;

import com.crocobet.notifications.dto.customerDTO.CustomerAddRequest;
import com.crocobet.notifications.dto.preferenceDTO.PreferenceDTO;
import com.crocobet.notifications.model.User;

import com.crocobet.notifications.model.address.Address;
import com.crocobet.notifications.model.address.AddressType;
import com.crocobet.notifications.model.enums.Role;

import com.crocobet.notifications.model.preference.Preference;
import com.crocobet.notifications.repository.AddressRepository;
import com.crocobet.notifications.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private AddressRepository addressRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
    }

    public void registerUser(UserRegistrationRequest registrationRequest){

        if(registrationRequest.getUsername() != null && userRepository.findByUsername(registrationRequest.getUsername()).isEmpty()){
            User user = new User();
            user.setUsername(registrationRequest.getUsername());
            user.setMail(registrationRequest.getMail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(Role.ADMIN);
            userRepository.save(user);

        }else{
            throw new IllegalArgumentException("მოხმარებელის სახელი უკვე გამოყენებულია. გთხოვთ სცადოთ თავიდან");
        }

    }

    @Transactional
    public void addCustomer(CustomerAddRequest request){
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setLegalAddress(request.getLegalAddress());
        user.setMail(request.getMail());
        user.setNumber(request.getNumber());
        user.setRole(Role.CUSTOMER);

        List<Address> addresses = new ArrayList<>();
        for(AddressDTO dto : request.getAddressDTOList()){
            Address address = new Address();
            address.setAddressType(dto.getAddressType());
            address.setUser(user);
            addresses.add(address);
        }

        List<Preference> preferences = new ArrayList<>();
        for(PreferenceDTO dto : request.getPreferenceDTOList()){
            Preference preference = new Preference();
            preference.setOptedIn(dto.getOptedIn());

            preference.setUser(user);
            preference.setPreferenceType(dto.getPreferenceType());
            preferences.add(preference);
        }
        user.setAddress(addresses);
        user.setPreference(preferences);

        userRepository.save(user);
    }

    public void removeCustomer(Long customerId){
        User user = userRepository.findById(customerId).orElseThrow();
        userRepository.delete(user);
    }

    public void updateCustomer(Long customerId, CustomerAddRequest request) {
        User user = userRepository.findById(customerId).orElseThrow();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        user.setLegalAddress(request.getLegalAddress())
        ;
        user.setMail(request.getMail());
        user.setNumber(request.getNumber());

        if (request.getAddressDTOList() != null) {
            updateAddresses(user, request.getAddressDTOList());
        }

        if (request.getPreferenceDTOList() != null) {
            updatePreferences(user, request.getPreferenceDTOList());
        }

        userRepository.save(user);
    }

    private void updateAddresses(User user, List<AddressDTO> addressDTOs) {


        Map<Long, Address> existingAddresses = user.getAddress().stream()
                .collect(Collectors.toMap(

                        addr -> addr.getAddressType().getAddressTypeId(),
                        addr -> addr
                ));

        List<Address> updatedAddresses = new ArrayList<>();

        for (AddressDTO dto : addressDTOs) {
            Long typeId = dto.getAddressType().getAddressTypeId();



            Address address = existingAddresses.getOrDefault(typeId, new Address());
            address.setAddressType(dto.getAddressType());
            address.setUser(user);


            updatedAddresses.add(address);
        }

        user.setAddress(updatedAddresses);
    }

    private void updatePreferences(User user, List<PreferenceDTO> preferenceDTOs) {

        Map<Long, Preference> existingPreferences = user.getPreference().stream()
                .collect(Collectors.toMap(
                        pref -> pref.getPreferenceType().getPreferenceTypeId(),
                        pref -> pref
                ));

        List<Preference> updatedPreferences = new ArrayList<>();

        for (PreferenceDTO dto : preferenceDTOs) {
            if (dto.getPreferenceType() == null) continue;

            Long typeId = dto.getPreferenceType().getPreferenceTypeId();

            Preference preference = existingPreferences.getOrDefault(typeId, new Preference());
            preference.setPreferenceType(dto.getPreferenceType());
            preference.setOptedIn(dto.getOptedIn());
            preference.setUser(user);
            updatedPreferences.add(preference);
        }

        user.setPreference(updatedPreferences);
        userRepository.save(user);
    }


    public User getUserById(Long customerId){
        return userRepository.findById(customerId).orElseThrow();
    }

    public List<User> getAllCustomers(){
        return userRepository.findAllCustomers();
    }

    public List<User> getFilteredCustomers(String keyword){
        return userRepository.findFilteredCustomers(keyword);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow();
    }

}
