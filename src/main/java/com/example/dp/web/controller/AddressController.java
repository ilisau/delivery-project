package com.example.dp.web.controller;

import com.example.dp.domain.user.Address;
import com.example.dp.service.AddressService;
import com.example.dp.web.dto.user.AddressDto;
import com.example.dp.web.dto.user.CreateAddressDto;
import com.example.dp.web.mapper.AddressMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/addresses")
@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public AddressDto create(@Valid @RequestBody CreateAddressDto createAddressDto) {
        Address address = addressService.create(createAddressDto);
        return AddressMapper.INSTANCE.toDto(address);
    }

    @GetMapping("/{id}")
    public AddressDto getById(@PathVariable Long id) {
        Address address = addressService.getById(id);
        return AddressMapper.INSTANCE.toDto(address);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        addressService.deleteById(id);
    }
}
