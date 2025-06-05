package com.crocobet.notifications.dto.customerDTO;

import com.crocobet.notifications.dto.addressDTO.AddressDTO;

import com.crocobet.notifications.dto.preferenceDTO.PreferenceDTO;

import java.util.List;

public class CustomerAddRequest {

    private String firstName;

    private String lastName;

    private String mail;

    private String number;

    private String legalAddress;

    private List<PreferenceDTO> preferenceDTOList;

    private List<AddressDTO> addressDTOList;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public List<PreferenceDTO> getPreferenceDTOList() {
        return preferenceDTOList;
    }

    public void setPreferenceDTOList(List<PreferenceDTO> preferenceDTOList) {
        this.preferenceDTOList = preferenceDTOList;
    }

    public List<AddressDTO> getAddressDTOList() {
        return addressDTOList;
    }

    public void setAddressDTOList(List<AddressDTO> addressDTOList) {
        this.addressDTOList = addressDTOList;
    }
}
