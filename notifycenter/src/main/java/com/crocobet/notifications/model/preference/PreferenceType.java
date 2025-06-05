package com.crocobet.notifications.model.preference;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="preferenceTypes")
public class PreferenceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceTypeId;

    private String preferenceTypeName;

    @OneToMany(mappedBy = "preferenceType")
    private List<Preference> preference;

    public Long getPreferenceTypeId() {
        return preferenceTypeId;
    }

    public void setPreferenceTypeId(Long preferenceTypeId) {
        this.preferenceTypeId = preferenceTypeId;
    }

    public String getPreferenceTypeName() {
        return preferenceTypeName;
    }

    public void setPreferenceTypeName(String preferenceTypeName) {
        this.preferenceTypeName = preferenceTypeName;
    }

    public List<Preference> getPreference() {
        return preference;
    }

    public void setPreference(List<Preference> preference) {
        this.preference = preference;
    }
}
