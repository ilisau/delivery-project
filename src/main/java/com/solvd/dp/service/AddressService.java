package com.solvd.dp.service;

import com.solvd.dp.domain.user.Address;

import java.util.List;

public interface AddressService {

    Address getById(Long id);

    List<Address> getAllByUserId(Long userId);

    List<Address> getAllByRestaurantId(Long restaurantId);

    Address update(Address address);

    Address create(Address address);

    boolean isUserOwner(Long addressId, Long userId);

    boolean isEmployeeOwner(Long addressId, Long employeeId);

    void delete(Long id);

}
