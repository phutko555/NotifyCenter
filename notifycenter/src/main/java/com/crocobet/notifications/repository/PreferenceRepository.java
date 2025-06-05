package com.crocobet.notifications.repository;

import com.crocobet.notifications.model.preference.Preference;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference,Long> {

    @Query("SELECT p FROM Preference p WHERE p.preferenceType.preferenceTypeName = :preferenceTypeName")
    Preference findByPreferenceTypeName(String preferenceTypeName);
}
