package com.example.dp.service.impl;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.user.Address;
import com.example.dp.repository.AddressRepository;
import com.example.dp.service.AddressService;
import com.example.dp.web.dto.user.CreateAddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address getById(Long id) throws ResourceNotFoundException {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found for this id :: " + id));
    }

    @Override
    public List<Address> getAllByUserId(Long userId) {
        return addressRepository.getAllByUserId(userId);
    }

    @Override
    public List<Address> getAllByRestaurantId(Long restaurantId) {
        return addressRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address create(CreateAddressDto createAddressDto) {
        return addressRepository.create(createAddressDto);
    }

    @Override
    public void deleteById(Long id) {
        addressRepository.delete(id);
    }
}
