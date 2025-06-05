package com.crocobet.notifications.controller.User;

import com.crocobet.notifications.model.User;

import com.crocobet.notifications.model.address.Address;

import com.crocobet.notifications.model.address.AddressType;

import com.crocobet.notifications.model.preference.Preference;

import com.crocobet.notifications.model.preference.PreferenceType;

import com.crocobet.notifications.service.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private AddressTypeService addressTypeService;
    private PreferenceTypeService preferenceTypeService;
    private AddressService addressService;
    private UserService userService;
    private PreferenceService preferenceService;
    private ReportingService reportingService;
    private NotificationService notificationService;

    @Autowired
    public AdminController(AddressTypeService addressTypeService, PreferenceTypeService preferenceTypeService, AddressService addressService, UserService userService, PreferenceService preferenceService, ReportingService reportingService, NotificationService notificationService) {
        this.addressTypeService = addressTypeService;
        this.preferenceTypeService = preferenceTypeService;
        this.addressService = addressService;
        this.userService = userService;
        this.preferenceService = preferenceService;
        this.reportingService = reportingService;
        this.notificationService = notificationService;
    }

    @GetMapping("/dashboard")
    public String getAdminPanelPage(Principal principal,Model model){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        return "admin-panel";
    }

    @GetMapping("/customers")
    public String getCustomersPage(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        List<User> allUsers = userService.getAllCustomers();
        List<Address> addresses = addressService.addresses();
        List<PreferenceType> preferenceTypes = preferenceTypeService.getPreferenceTypes();
        List<AddressType> addressTypes = addressTypeService.getAddressTypes();
        model.addAttribute("addresses",addresses);
        model.addAttribute("preferenceTypes",preferenceTypes);
        model.addAttribute("addressTypes",addressTypes);
        model.addAttribute("customers",allUsers);
        return "admin-panel-customers";
    }

    @GetMapping("/addresses")
    public String getAddressesPage(Model model,Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        List<Address> addresses = addressService.addresses();
        model.addAttribute("addresses",addresses);
        return "admin-panel-addresses";
    }

    @GetMapping("/preferences")
    public String getPreferencesPage(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        List<Preference> preferences = preferenceService.preferenceList();
        model.addAttribute("preferences",preferences);

        return "admin-panel-preferences";
    }

    @GetMapping("/update/{customerId}")
    public String updateCustomerPage(@PathVariable Long customerId, Model model,Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        User customer = userService.getUserById(customerId);
        model.addAttribute("customer",customer);

        List<PreferenceType> preferenceTypes = preferenceTypeService.getPreferenceTypes();
        List<AddressType> addressTypes = addressTypeService.getAddressTypes();
        model.addAttribute("preferenceTypes",preferenceTypes);
        model.addAttribute("addressTypes",addressTypes);
        return "admin-panel-update-page";
    }

    @GetMapping("/reporting")
    public String getReportingPage(Model model,Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        List<Object[]> reports = reportingService.countOptedOutCustomersPerNotificationType();
        model.addAttribute("reports",reports);
        return "admin-panel-reporting";
    }

    @GetMapping("/reporting/notifications")
    public String getNotificationReportingPage(Model model,Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);

        double notificationSuccessRate = notificationService.getDeliverySuccessRate();
        double notificationFailedRate = notificationService.calculateDeliveryFailedRate();
        double notificationPendingRate = notificationService.calculateDeliveryPendingRate();

        model.addAttribute("successRate",notificationSuccessRate);

        model.addAttribute("failedRate",notificationFailedRate);
        model.addAttribute("pendingRate",notificationPendingRate);

        model.addAttribute("successTab",true);
        return "admin-panel-reporting";
    }

}
