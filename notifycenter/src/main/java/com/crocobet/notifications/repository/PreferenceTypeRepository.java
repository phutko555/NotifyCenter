package com.crocobet.notifications.repository;

import com.crocobet.notifications.model.preference.PreferenceType;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceTypeRepository extends JpaRepository<PreferenceType, Long> {

    @Query("SELECT p.preferenceType.preferenceTypeName, " +
            "SUM(CASE WHEN p.optedIn = true THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN p.optedIn = false THEN 1 ELSE 0 END) " +
            "FROM Preference p " +
            "GROUP BY p.preferenceType.preferenceTypeName")
    List<Object[]> countOptedInAndOutCustomersPerNotificationType();


}
