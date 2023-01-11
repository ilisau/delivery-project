package com.solvd.dp.repository;

import com.solvd.dp.domain.user.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {

    Optional<Address> findById(Long id);

    List<Address> getAllByUserId(Long userId);

    List<Address> getAllByRestaurantId(Long restaurantId);

    Address save(Address address);

    Address create(Address address);

    void delete(Long id);

}
