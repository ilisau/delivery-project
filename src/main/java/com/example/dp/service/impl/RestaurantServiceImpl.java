package com.example.dp.service.impl;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.repository.RestaurantRepository;
import com.example.dp.service.RestaurantService;
import com.example.dp.web.dto.restaurant.CreateRestaurantDto;
import com.example.dp.web.dto.user.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;


    @Override
    public Restaurant getById(Long id) throws ResourceNotFoundException {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + id));
    }

    @Override
    public Restaurant getByName(String name) throws ResourceNotFoundException {
        return restaurantRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this name :: " + name));
    }

    @Override
    public Restaurant save(Restaurant restaurantDto) {
        return restaurantRepository.save(restaurantDto);
    }

    @Override
    public Restaurant create(CreateRestaurantDto createRestaurantDto) {
        Restaurant restaurant = restaurantRepository.create(createRestaurantDto);
        for (AddressDto dto : createRestaurantDto.getAddresses()) {
            restaurantRepository.addAddressById(restaurant.getId(), dto.getId());
        }
        return restaurant;
    }

    @Override
    public void addEmployeeById(Long id, Long employeeId) {
        restaurantRepository.addEmployeeById(id, employeeId);
    }

    @Override
    public void deleteEmployeeById(Long id, Long employeeId) {
        restaurantRepository.deleteEmployeeById(id, employeeId);
    }

    @Override
    public void addItemById(Long id, Long itemId) {
        restaurantRepository.addItemById(id, itemId);
    }

    @Override
    public void deleteItemById(Long id, Long itemId) {
        restaurantRepository.deleteItemById(id, itemId);
    }

    @Override
    public void addAddressById(Long id, Long addressId) {
        restaurantRepository.addAddressById(id, addressId);
    }

    @Override
    public void deleteAddressById(Long id, Long addressId) {
        restaurantRepository.deleteAddressById(id, addressId);
    }

    @Override
    public void deleteById(Long id) {
        restaurantRepository.delete(id);
    }
}
