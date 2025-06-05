package com.crocobet.notifications.controller.address;

import com.crocobet.notifications.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/address")
public class AddressController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @DeleteMapping("remove/{addressId}")
    public String removeAddress(@PathVariable Long addressId){
        addressService.removeAddress(addressId);
        return "redirect:/admin/addresses";
    }
}
