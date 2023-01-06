package com.example.dp.service;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.user.Address;
import com.example.dp.web.dto.user.AddressDto;
import com.example.dp.web.dto.user.CreateAddressDto;

public interface AddressService {

    Address getById(Long id) throws ResourceNotFoundException;

    Address save(Address address);

    Address create(CreateAddressDto createAddressDto);

    void deleteById(Long id);
}
