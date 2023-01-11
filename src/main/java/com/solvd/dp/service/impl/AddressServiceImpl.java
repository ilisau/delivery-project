package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.user.Address;
import com.solvd.dp.repository.AddressRepository;
import com.solvd.dp.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    @Transactional(readOnly = true)
    public Address getById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found for this id :: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> getAllByUserId(Long userId) {
        return addressRepository.getAllByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> getAllByRestaurantId(Long restaurantId) {
        return addressRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    @Transactional
    public Address save(Address address) {
        addressRepository.save(address);
        return address;
    }

    @Override
    @Transactional
    public Address create(Address address) {
        addressRepository.create(address);
        return address;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        addressRepository.delete(id);
    }

}
