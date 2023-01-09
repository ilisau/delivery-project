package com.example.dp.service.impl;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.repository.RestaurantRepository;
import com.example.dp.service.AddressService;
import com.example.dp.service.EmployeeService;
import com.example.dp.service.ItemService;
import com.example.dp.service.RestaurantService;
import com.example.dp.web.dto.restaurant.CreateRestaurantDto;
import com.example.dp.web.dto.user.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ItemService itemService;
    private final AddressService addressService;
    private final EmployeeService employeeService;

    @Override
    public Restaurant getById(Long id) throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + id));
        restaurant.setItems(itemService.getAllByRestaurantId(id));
        restaurant.setAddresses(addressService.getAllByRestaurantId(id));
        restaurant.setEmployees(employeeService.getAllByRestaurantId(id));
        return restaurant;
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
