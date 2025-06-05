package com.crocobet.notifications.wrapper;

import com.crocobet.notifications.model.preference.PreferenceType;

import java.util.List;

public class PreferenceTypeListWrapper {
    private List<PreferenceType> preferenceTypes;

    public List<PreferenceType> getPreferenceTypes() {
        return preferenceTypes;
    }

    public void setPreferenceTypes(List<PreferenceType> preferenceTypes) {
        this.preferenceTypes = preferenceTypes;
    }
}
