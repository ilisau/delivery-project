package com.example.dp.web.controller.user;

import com.example.dp.domain.user.Address;
import com.example.dp.service.AddressService;
import com.example.dp.web.dto.mapper.AddressMapper;
import com.example.dp.web.dto.user.AddressDto;
import com.example.dp.web.dto.user.CreateAddressDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.dp.web.controller.user.AddressURLs.BASE_URL;
import static com.example.dp.web.controller.user.AddressURLs.ID_URL;

@RequestMapping(BASE_URL)
@RestController
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public AddressDto createAddress(@Valid @RequestBody CreateAddressDto createAddressDto) {
        return AddressMapper.INSTANCE.toDto(addressService.create(createAddressDto));
    }

    @GetMapping(ID_URL)
    public AddressDto getAddressById(@PathVariable Long id) {
        return AddressMapper.INSTANCE.toDto(addressService.getById(id));
    }

    @DeleteMapping(ID_URL)
    public void deleteAddressById(@PathVariable Long id) {
        addressService.deleteById(id);
    }
}
