package com.example.dp.repository;

import com.example.dp.domain.user.Address;
import com.example.dp.web.dto.user.CreateAddressDto;

import java.util.Optional;

public interface AddressRepository {

    Optional<Address> findById(Long id);

    Address save(Address address);

    Address create(CreateAddressDto addressDto);

    void delete(Long id);
}
