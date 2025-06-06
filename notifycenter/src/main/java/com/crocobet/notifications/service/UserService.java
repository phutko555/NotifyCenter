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

import java.time.LocalDateTime;
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
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.CUSTOMER);


        Set<Long> seenAddressTypeIds = new HashSet<>();
        List<Address> addresses = new ArrayList<>();

        for (AddressDTO dto : request.getAddressDTOList()) {
            if (dto.getAddressType() == null || dto.getAddressType().getAddressTypeId() == null) {
                continue;
            }

            Long typeId = dto.getAddressType().getAddressTypeId();
            if (seenAddressTypeIds.contains(typeId)) {
                continue;
            }

            seenAddressTypeIds.add(typeId);

            Address address = new Address();
            address.setAddressType(dto.getAddressType());
            address.setUser(user);
            addresses.add(address);
        }

        Set<Long> seenPreferenceTypeIds = new HashSet<>();
        List<Preference> preferences = new ArrayList<>();

        for (PreferenceDTO dto : request.getPreferenceDTOList()) {
            if (dto.getPreferenceType() == null || dto.getPreferenceType().getPreferenceTypeId() == null) {
                continue;
            }

            Long typeId = dto.getPreferenceType().getPreferenceTypeId();
            if (seenPreferenceTypeIds.contains(typeId)) {
                continue;
            }

            seenPreferenceTypeIds.add(typeId);

            Preference preference = new Preference();
            Boolean optedIn = dto.getOptedIn();
            preference.setOptedIn(optedIn != null ? optedIn : false);
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
        Map<Long, Address> existingAddressesById = user.getAddress().stream()
                .filter(a -> a.getAddressId() != null)
                .collect(Collectors.toMap(Address::getAddressId, a -> a));

        List<Address> updatedAddresses = new ArrayList<>();
        for (AddressDTO dto : addressDTOs) {

            Address address;

            if (dto.getAddressId() != null && existingAddressesById.containsKey(dto.getAddressId())) {

                address = existingAddressesById.get(dto.getAddressId());
            } else {
                address = new Address();
            }
            address.setAddressType(dto.getAddressType());
            address.setUser(user);
            updatedAddresses.add(address);
        }

        user.setAddress(updatedAddresses);
    }


    private void updatePreferences(User user, List<PreferenceDTO> preferenceDTOs) {
        Map<Long, Preference> existingPreferencesById = user.getPreference().stream()
                .filter(p -> p.getPreferenceId() != null)
                .collect(Collectors.toMap(Preference::getPreferenceId, p -> p));

        Map<Long, Preference> existingPreferencesByTypeId = user.getPreference().stream()
                .filter(p -> p.getPreferenceType() != null)
                .collect(Collectors.toMap(
                        p -> p.getPreferenceType().getPreferenceTypeId(),
                        p -> p,
                        (p1, p2) -> p1
                ));

        List<Preference> updatedPreferences = new ArrayList<>();

        for (PreferenceDTO dto : preferenceDTOs) {
            if (dto.getPreferenceType() == null) continue;

            Preference preference;

            if (dto.getPreferenceId() != null && existingPreferencesById.containsKey(dto.getPreferenceId())) {
                preference = existingPreferencesById.get(dto.getPreferenceId());
            }

            else if (existingPreferencesByTypeId.containsKey(dto.getPreferenceType().getPreferenceTypeId())) {
                preference = existingPreferencesByTypeId.get(dto.getPreferenceType().getPreferenceTypeId());
            }

            else {
                preference = new Preference();
            }

            preference.setPreferenceType(dto.getPreferenceType());
            preference.setOptedIn(dto.getOptedIn());
            preference.setUser(user);

            updatedPreferences.add(preference);
        }

        user.setPreference(updatedPreferences);
    }



    public User getUserById(Long customerId){
        return userRepository.findById(customerId).orElseThrow();
    }

    public List<User> getAllCustomers(){
        return userRepository.findAllCustomers();
    }

    public List<User> getFilteredCustomers(String keyword, Long preferenceType, String sortBy) {
        List<User> users = userRepository.findFilteredCustomers(keyword, preferenceType);

        if (sortBy != null) {
            switch (sortBy) {
                case "firstNameAsc":
                    users.sort(Comparator.comparing(User::getFirstName));
                    break;
                case "firstNameDesc":
                    users.sort(Comparator.comparing(User::getFirstName).reversed());
                    break;
                case "createdAtAsc":
                    users.sort(Comparator.comparing(User::getCreatedAt));
                    break;
                case "createdAtDesc":
                    users.sort(Comparator.comparing(User::getCreatedAt).reversed());
                    break;
                default:
                    break;
            }
        }

        return users;
    }



    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow();
    }

}
