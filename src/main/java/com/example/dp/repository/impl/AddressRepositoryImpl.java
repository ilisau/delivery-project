package com.example.dp.repository.impl;

import com.example.dp.domain.user.Address;
import com.example.dp.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressRepositoryImpl implements AddressRepository {

    @Override
    public Optional<Address> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Address save(Address addressDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
