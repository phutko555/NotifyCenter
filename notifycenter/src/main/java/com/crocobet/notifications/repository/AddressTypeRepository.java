package com.crocobet.notifications.repository;

import com.crocobet.notifications.model.address.AddressType;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType,Long> {

    Boolean existsByAddressTypeName(String addressTypeName);
}
