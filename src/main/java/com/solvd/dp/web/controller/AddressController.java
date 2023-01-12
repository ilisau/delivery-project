package com.solvd.dp.web.controller;

import com.solvd.dp.domain.user.Address;
import com.solvd.dp.service.AddressService;
import com.solvd.dp.web.dto.user.AddressDto;
import com.solvd.dp.web.dto.validation.OnUpdate;
import com.solvd.dp.web.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/addresses")
@RestController
@RequiredArgsConstructor
@Validated
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @PutMapping
    public void save(@Validated(OnUpdate.class) @RequestBody AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        addressService.save(address);
    }

    @GetMapping("/{id}")
    public AddressDto getById(@PathVariable Long id) {
        Address address = addressService.getById(id);
        return addressMapper.toDto(address);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        addressService.delete(id);
    }

    @GetMapping("/users/{id}")
    public List<AddressDto> getAllByUserId(@PathVariable Long id) {
        List<Address> addresses = addressService.getAllByUserId(id);
        return addressMapper.toDto(addresses);
    }

    @GetMapping("/restaurants/{id}")
    public List<AddressDto> getAllByRestaurantId(@PathVariable Long id) {
        List<Address> addresses = addressService.getAllByRestaurantId(id);
        return addressMapper.toDto(addresses);
    }

}
