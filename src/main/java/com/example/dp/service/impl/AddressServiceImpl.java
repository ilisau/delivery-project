package com.example.dp.service.impl;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.user.Address;
import com.example.dp.repository.AddressRepository;
import com.example.dp.service.AddressService;
import com.example.dp.web.dto.mapper.AddressMapper;
import com.example.dp.web.dto.mapper.CreateAddressMapper;
import com.example.dp.web.dto.user.AddressDto;
import com.example.dp.web.dto.user.CreateAddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address getById(Long id) throws ResourceNotFoundException {
        return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found for this id :: " + id));
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
