package com.crocobet.notifications.service;

import com.crocobet.notifications.model.User;

import com.crocobet.notifications.model.preference.Preference;

import com.crocobet.notifications.repository.PreferenceRepository;

import com.crocobet.notifications.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

import java.util.NoSuchElementException;

@Service
public class PreferenceService {
    private PreferenceRepository preferenceRepository;

    private UserRepository userRepository;

    @Autowired
    public PreferenceService(PreferenceRepository preferenceRepository, UserRepository userRepository) {
        this.preferenceRepository = preferenceRepository;
        this.userRepository = userRepository;
    }

    public List<Preference> preferenceList(){
     return  preferenceRepository.findAll();
    }


    @Transactional
    public void updateCustomerPreferences(Long customerId, Long preferenceTypeId, boolean optedIn){
        User user = userRepository.findById(customerId).orElseThrow();

        Preference targetPref = user.getPreference().stream()
                .filter(pref -> preferenceTypeId.equals(pref.getPreferenceType().getPreferenceTypeId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Preference არ მოიძებნა"));

        targetPref.setOptedIn(optedIn);
        userRepository.save(user);
    }
}