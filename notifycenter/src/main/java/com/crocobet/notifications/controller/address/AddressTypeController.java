package com.crocobet.notifications.controller.address;

import com.crocobet.notifications.service.AddressTypeService;

import com.crocobet.notifications.wrapper.AddressTypeListWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/addressType")
public class AddressTypeController {

    private AddressTypeService addressTypeService;

    @Autowired
    public AddressTypeController(AddressTypeService addressTypeService) {
        this.addressTypeService = addressTypeService;
    }

    @PostMapping("/add")
    public String addAddressType(@ModelAttribute("addressTypes")AddressTypeListWrapper wrapper, RedirectAttributes redirectAttributes){
        try{
            addressTypeService.addAddressType(wrapper.getAddressTypes());
            redirectAttributes.addFlashAttribute("successMessage","მისამართი წარმატებით დაემატა");

        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute(e.getMessage());
        }
        return "redirect:/admin/addresses";
    }

}
