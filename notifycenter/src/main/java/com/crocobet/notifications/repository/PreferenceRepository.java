package com.crocobet.notifications.repository;

import com.crocobet.notifications.model.preference.Preference;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference,Long> {

    @Query("SELECT p FROM Preference p WHERE p.preferenceType.preferenceTypeName = :preferenceTypeName")
    Preference findByPreferenceTypeName(String preferenceTypeName);

    List<Preference> findByUser_UserIdInAndPreferenceType_PreferenceTypeId(List<Long> userIds, Long preferenceTypeId);

}
