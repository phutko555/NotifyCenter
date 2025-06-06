package com.crocobet.notifications.dto.preferenceDTO;

import com.crocobet.notifications.model.preference.PreferenceType;

public class PreferenceDTO {

    private Long preferenceId;

    private PreferenceType preferenceType;

    private Boolean optedIn;

    public PreferenceType getPreferenceType() {
        return preferenceType;
    }

    public void setPreferenceType(PreferenceType preferenceType) {
        this.preferenceType = preferenceType;
    }

    public Boolean getOptedIn() {
        return optedIn;
    }

    public void setOptedIn(Boolean optedIn) {
        this.optedIn = optedIn;
    }

    public Long getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }
}
