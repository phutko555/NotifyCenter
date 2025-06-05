package com.crocobet.notifications.controller.preference;

import com.crocobet.notifications.dto.preferenceDTO.PreferenceUpdateRequest;
import com.crocobet.notifications.model.address.Address;
import com.crocobet.notifications.model.preference.Preference;
import com.crocobet.notifications.service.PreferenceService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceRestController {
    private PreferenceService preferenceService;

    @Autowired
    public PreferenceRestController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }


    @PutMapping("/update/{customerId}")
    public ResponseEntity<String> updatePreference(
            @PathVariable Long customerId,
            @RequestBody PreferenceUpdateRequest request) {
        try {
            preferenceService.updateCustomerPreferences(customerId, request.getPreferenceTypeId(), request.isOptedIn());
            return ResponseEntity.ok("მომხმარებლის სტატუსი წარმატებით განახლდა");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/preferences")
    public ResponseEntity<List<Preference>> getPreferences() {
        List<Preference> preferences = preferenceService.preferenceList();
        if (preferences.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(preferences);
    }


}