package com.crocobet.notifications.dto.preferenceDTO;

import java.util.List;

public class BatchPreferenceUpdateRequest {
    private List<Long> customerIds;
    private Long preferenceTypeId;
    private boolean optedIn;

    public List<Long> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }

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
