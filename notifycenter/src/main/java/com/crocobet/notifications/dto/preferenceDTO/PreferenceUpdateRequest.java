package com.crocobet.notifications.dto.preferenceDTO;

public class PreferenceUpdateRequest {

    private Long preferenceTypeId;
    private boolean optedIn;

    public Long getPreferenceTypeId() {
        return preferenceTypeId;
    }

    public void setPreferenceTypeId(Long preferenceTypeId) {
        this.preferenceTypeId = preferenceTypeId;
    }

    public boolean isOptedIn() {
        return optedIn;
    }

    public void setOptedIn(boolean optedIn) {
        this.optedIn = optedIn;
    }
}
