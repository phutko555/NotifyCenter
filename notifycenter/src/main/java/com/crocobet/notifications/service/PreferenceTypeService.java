package com.crocobet.notifications.service;

import com.crocobet.notifications.model.preference.PreferenceType;

import com.crocobet.notifications.repository.PreferenceTypeRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenceTypeService {
    private PreferenceTypeRepository repository;

    @Autowired
    public PreferenceTypeService(PreferenceTypeRepository repository) {
        this.repository = repository;
    }

    public List<PreferenceType> getPreferenceTypes(){
        return repository.findAll();
    }
}
