package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceAlreadyExistsException;
import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.restaurant.Restaurant;
import com.solvd.dp.domain.user.Address;
import com.solvd.dp.repository.RestaurantRepository;
import com.solvd.dp.service.AddressService;
import com.solvd.dp.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AddressService addressService;

    @Override
    @Transactional(readOnly = true)
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Restaurant getById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Restaurant getByName(String name) {
        return restaurantRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this name :: " + name));
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (restaurantRepository.exists(restaurant)) {
            throw new ResourceAlreadyExistsException("Restaurant with this name already exists");
        }
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    @Override
    @Transactional
    public Restaurant create(Restaurant restaurant) {
        if (restaurantRepository.exists(restaurant)) {
            throw new ResourceAlreadyExistsException("Restaurant already exists :: " + restaurant.getName());
        }
        restaurantRepository.create(restaurant);
        restaurant.setItems(new ArrayList<>());
        for (Address address : restaurant.getAddresses()) {
            addressService.create(address);
            restaurantRepository.addAddressById(restaurant.getId(), address.getId());
        }
        return restaurant;
    }

    @Override
    @Transactional
    public void addEmployeeById(Long restaurantId, Long employeeId) {
        if (restaurantRepository.employeeExists(restaurantId, employeeId)) {
            throw new ResourceAlreadyExistsException("Employee already exists for this restaurant :: " + restaurantId);
        }
        restaurantRepository.addEmployeeById(restaurantId, employeeId);
    }

    @Override
    @Transactional
    public void deleteEmployeeById(Long restaurantId, Long employeeId) {
        restaurantRepository.deleteEmployeeById(restaurantId, employeeId);
    }

    @Override
    @Transactional
    public void addItemById(Long restaurantId, Long itemId) {
        restaurantRepository.addItemById(restaurantId, itemId);
    }

    @Override
    @Transactional
    public void deleteItemById(Long restaurantId, Long itemId) {
        restaurantRepository.deleteItemById(restaurantId, itemId);
    }

    @Override
    @Transactional
    public void addAddress(Long restaurantId, Address address) {
        addressService.create(address);
        restaurantRepository.addAddressById(restaurantId, address.getId());
    }

    @Override
    @Transactional
    public void deleteAddressById(Long restaurantId, Long addressId) {
        restaurantRepository.deleteAddressById(restaurantId, addressId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        restaurantRepository.delete(id);
    }

}
