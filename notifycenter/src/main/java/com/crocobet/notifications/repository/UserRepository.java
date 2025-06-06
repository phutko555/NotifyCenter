package com.crocobet.notifications.repository;


import com.crocobet.notifications.model.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.role = CUSTOMER")
    List<User> findAllCustomers();


    @Transactional
    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FETCH u.preference p " +
            "WHERE (" +
            ":keyword IS NULL OR " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            ") " +
            "AND (:preferenceType IS NULL OR p.preferenceType.preferenceTypeId = :preferenceType) " +
            "AND u.role = 'CUSTOMER'")
    List<User> findFilteredCustomers(@Param("keyword") String keyword,
                                     @Param("preferenceType") Long preferenceType);


}
