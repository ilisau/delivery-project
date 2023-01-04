package com.example.dp.repository;

import com.example.dp.domain.user.Address;

import java.util.Optional;

public interface AddressRepository {

    Optional<Address> findById(Long id);

    Address save(Address address);

    void delete(Long id);
}
