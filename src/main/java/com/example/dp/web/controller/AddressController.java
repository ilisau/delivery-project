package com.example.dp.web.controller;

import com.example.dp.domain.user.Address;
import com.example.dp.service.AddressService;
import com.example.dp.web.dto.user.AddressDto;
import com.example.dp.web.dto.user.CreateAddressDto;
import com.example.dp.web.mapper.AddressMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/addresses")
@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PutMapping
    public void save(@Valid @RequestBody AddressDto addressDto) {
        Address address = AddressMapper.INSTANCE.toEntity(addressDto);
        addressService.save(address);
    }

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

    @GetMapping("/by-user/{id}")
    public List<AddressDto> getAllByUserId(@PathVariable Long id) {
        List<Address> addresses = addressService.getAllByUserId(id);
        return addresses.stream()
                .map(AddressMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-restaurant/{id}")
    public List<AddressDto> getAllByRestaurantId(@PathVariable Long id) {
        List<Address> addresses = addressService.getAllByRestaurantId(id);
        return addresses.stream()
                .map(AddressMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}
