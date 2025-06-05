package com.crocobet.notifications.repository;

import com.crocobet.notifications.model.address.Address;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
