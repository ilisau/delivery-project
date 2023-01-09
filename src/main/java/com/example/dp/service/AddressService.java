package com.example.dp.service;

import com.example.dp.domain.user.Address;
import com.example.dp.web.dto.user.CreateAddressDto;

import java.util.List;

public interface AddressService {

    Address getById(Long id);

    List<Address> getAllByUserId(Long userId);

    List<Address> getAllByRestaurantId(Long restaurantId);

    Address save(Address address);

    Address create(CreateAddressDto createAddressDto);

    void deleteById(Long id);
}
