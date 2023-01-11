package com.solvd.dp.service;

import com.solvd.dp.domain.user.Address;

import java.util.List;

public interface AddressService {

    Address getById(Long id);

    List<Address> getAllByUserId(Long userId);

    List<Address> getAllByRestaurantId(Long restaurantId);

    Address save(Address address);

    Address create(Address address);

    void delete(Long id);

}
