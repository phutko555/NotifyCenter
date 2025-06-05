package com.crocobet.notifications.service;

import com.crocobet.notifications.repository.PreferenceTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingService {

    private PreferenceTypeRepository preferenceTypeRepository;

    @Autowired
    public ReportingService(PreferenceTypeRepository preferenceTypeRepository) {
        this.preferenceTypeRepository = preferenceTypeRepository;
    }

    public List<Object[]> countOptedOutCustomersPerNotificationType(){
        return preferenceTypeRepository.countOptedInAndOutCustomersPerNotificationType();
    }
}
