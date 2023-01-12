package com.solvd.dp.web.controller;

import com.solvd.dp.domain.user.Address;
import com.solvd.dp.service.AddressService;
import com.solvd.dp.web.dto.OnCreate;
import com.solvd.dp.web.dto.OnUpdate;
import com.solvd.dp.web.dto.user.AddressDto;
import com.solvd.dp.web.mapper.AddressMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/addresses")
@RestController
@RequiredArgsConstructor
@Validated
public class AddressController {

    private final AddressService addressService;

    @PutMapping
    @Validated(OnUpdate.class)
    public void save(@Valid @RequestBody AddressDto addressDto) {
        Address address = AddressMapper.INSTANCE.toEntity(addressDto);
        addressService.save(address);
    }

    @GetMapping("/{id}")
    public AddressDto getById(@PathVariable Long id) {
        Address address = addressService.getById(id);
        return AddressMapper.INSTANCE.toDto(address);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        addressService.delete(id);
    }

    @GetMapping("/users/{id}")
    public List<AddressDto> getAllByUserId(@PathVariable Long id) {
        List<Address> addresses = addressService.getAllByUserId(id);
        return addresses.stream()
                .map(AddressMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/restaurants/{id}")
    public List<AddressDto> getAllByRestaurantId(@PathVariable Long id) {
        List<Address> addresses = addressService.getAllByRestaurantId(id);
        return addresses.stream()
                .map(AddressMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

}
