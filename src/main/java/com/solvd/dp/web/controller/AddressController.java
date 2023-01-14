package com.solvd.dp.web.controller;

import com.solvd.dp.domain.user.Address;
import com.solvd.dp.service.AddressService;
import com.solvd.dp.web.dto.user.AddressDto;
import com.solvd.dp.web.dto.validation.OnUpdate;
import com.solvd.dp.web.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/addresses")
@RestController
@RequiredArgsConstructor
@Validated
public class AddressController {

    private final AddressService addressService;

    private final AddressMapper addressMapper;

    @PutMapping
    public void update(@Validated(OnUpdate.class) @RequestBody AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        addressService.update(address);
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

}
