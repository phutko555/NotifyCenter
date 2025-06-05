package com.crocobet.notifications.controller.User;

import com.crocobet.notifications.dto.customerDTO.CustomerAddRequest;

import com.crocobet.notifications.model.User;
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


    private UserService userService;
    @Autowired
    public CustomerController(UserService userService) {
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
    public String getFilteredCustomers(@RequestParam String keyword, Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        List<User> filteredCustomers = userService.getFilteredCustomers(keyword);

        model.addAttribute("customers",filteredCustomers);
        model.addAttribute("keyword",keyword);

        return "admin-panel-customers";
    }

}
