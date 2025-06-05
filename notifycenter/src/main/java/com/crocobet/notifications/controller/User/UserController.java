package com.crocobet.notifications.controller.User;

import com.crocobet.notifications.dto.addressDTO.AddressDTO;

import com.crocobet.notifications.dto.adminDTO.UserRegistrationRequest;

import com.crocobet.notifications.dto.customerDTO.CustomerAddRequest;

import com.crocobet.notifications.dto.preferenceDTO.PreferenceDTO;

import com.crocobet.notifications.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage(){

        return "login-page";
    }

    @PostMapping("/registration")
    public String registerUser(UserRegistrationRequest registrationRequest, RedirectAttributes redirectAttributes){
        try{
            userService.registerUser(registrationRequest);
            redirectAttributes.addFlashAttribute("accCreated","გილოცავთ, თქვენ წარმატებით გაიარეთ რეგისტრაცია");

        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("registerError",e.getMessage());
            redirectAttributes.addFlashAttribute("registerTab", true);
        }

        return "redirect:/login";
    }

}