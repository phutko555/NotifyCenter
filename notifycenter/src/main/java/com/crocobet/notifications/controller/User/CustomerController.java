package com.crocobet.notifications.controller.User;

import com.crocobet.notifications.dto.customerDTO.CustomerAddRequest;

import com.crocobet.notifications.model.User;
import com.crocobet.notifications.model.address.AddressType;
import com.crocobet.notifications.model.preference.PreferenceType;
import com.crocobet.notifications.service.AddressTypeService;
import com.crocobet.notifications.service.PreferenceTypeService;
import com.crocobet.notifications.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private AddressTypeService addressTypeService;

    private PreferenceTypeService preferenceTypeService;

    private UserService userService;

    @Autowired
    public CustomerController(AddressTypeService addressTypeService, PreferenceTypeService preferenceTypeService, UserService userService) {
        this.addressTypeService = addressTypeService;
        this.preferenceTypeService = preferenceTypeService;
        this.userService = userService;
    }

    @PostMapping("/add-customer")
    public String addCustomer(CustomerAddRequest request){
        userService.addCustomer(request);
        return "redirect:/admin/customers";
    }

    @PutMapping("/update-customer/{customerId}")
    public String updateCustomer(@PathVariable Long customerId,CustomerAddRequest request){
        userService.updateCustomer(customerId,request);
        return "redirect:/admin/customers";
    }

    @DeleteMapping("/remove-customer/{customerId}")
    public String removeCustomer(@PathVariable Long customerId){
        userService.removeCustomer(customerId);
        return "redirect:/admin/customers";
    }

    @GetMapping("/filtered-customers")
    public String getFilteredCustomers(@RequestParam(required = false) String keyword,

                                       @RequestParam(required = false) String preferenceType,

                                       Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        List<User> filteredCustomers = userService.getFilteredCustomers(keyword,preferenceType);

        List<PreferenceType> preferenceTypes = preferenceTypeService.getPreferenceTypes();
        List<AddressType> addressTypes = addressTypeService.getAddressTypes();

        model.addAttribute("preferenceTypes",preferenceTypes);
        model.addAttribute("addressTypes",addressTypes);

        model.addAttribute("customers",filteredCustomers);
        model.addAttribute("keyword",keyword);

        return "admin-panel-customers";
    }

}
