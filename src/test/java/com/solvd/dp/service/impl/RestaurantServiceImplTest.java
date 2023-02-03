package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceAlreadyExistsException;
import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.restaurant.Restaurant;
import com.solvd.dp.domain.user.Address;
import com.solvd.dp.repository.RestaurantRepository;
import com.solvd.dp.service.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Test
    void getAll() {
        restaurantService.getAll();

        verify(restaurantRepository).getAll();
    }

    @Test
    void getByExistingId() {
        Long id = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);

        when(restaurantRepository.findById(id))
                .thenReturn(Optional.of(restaurant));

        assertEquals(restaurant, restaurantService.getById(id));
        verify(restaurantRepository).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;

        when(restaurantRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restaurantService.getById(id));
        verify(restaurantRepository).findById(id);
    }

    @Test
    void getByExistingName() {
        String name = "Restaurant";
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);

        when(restaurantRepository.findByName(name))
                .thenReturn(Optional.of(restaurant));

        assertEquals(restaurant, restaurantService.getByName(name));
        verify(restaurantRepository).findByName(name);
    }

    @Test
    void getByNotExistingName() {
        String name = "Restaurant";

        when(restaurantRepository.findByName(name))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restaurantService.getByName(name));
        verify(restaurantRepository).findByName(name);
    }

    @Test
    void updateExistingRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Restaurant");

        when(restaurantRepository.exists(restaurant))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> restaurantService.update(restaurant));
        verify(restaurantRepository).exists(restaurant);
        verify(restaurantRepository, never()).update(restaurant);
    }

    @Test
    void updateNotExistingRestaurant() {
        String name = "Restaurant";
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName(name);

        when(restaurantRepository.exists(restaurant))
                .thenReturn(false);

        doAnswer(invocation -> {
            Restaurant restaurant1 = invocation.getArgument(0);
            restaurant1.setName(name);
            return restaurant1;
        }).when(restaurantRepository).update(restaurant);

        restaurantService.update(restaurant);

        assertEquals(name, restaurant.getName());
        verify(restaurantRepository).exists(restaurant);
        verify(restaurantRepository).update(restaurant);
    }

    @Test
    void createExistingRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Restaurant");

        when(restaurantRepository.exists(restaurant))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> restaurantService.create(restaurant));
        verify(restaurantRepository).exists(restaurant);
        verify(restaurantRepository, never()).create(restaurant);
        verify(addressService, never()).create(any(Address.class));
        verify(restaurantRepository, never()).addAddressById(anyLong(), anyLong());
    }

    @Test
    void createNotExistingRestaurant() {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Restaurant");
        List<Address> addresses = List.of(new Address(1L, "Mogilevskaya", 1, null, null),
                new Address(2L, "Mogilevskaya", 2, null, null));
        restaurant.setAddresses(addresses);

        when(restaurantRepository.exists(restaurant))
                .thenReturn(false);

        doAnswer(invocation -> {
            Restaurant restaurant1 = invocation.getArgument(0);
            restaurant1.setId(restaurantId);
            return restaurant1;
        }).when(restaurantRepository).create(restaurant);

        AtomicLong id = new AtomicLong(1L);
        doAnswer(invocation -> {
            Address address = invocation.getArgument(0);
            address.setId(id.getAndIncrement());
            return address;
        }).when(addressService).create(any(Address.class));

        restaurantService.create(restaurant);

        verify(restaurantRepository).exists(restaurant);
        verify(restaurantRepository).create(restaurant);
        verify(addressService).create(restaurant.getAddresses().get(0));
        verify(addressService).create(restaurant.getAddresses().get(1));
        verify(restaurantRepository).addAddressById(restaurantId, 1L);
        verify(restaurantRepository).addAddressById(restaurantId, 2L);
    }

    @Test
    void addExistingEmployeeById() {
        Long restaurantId = 1L;
        Long employeeId = 1L;

        when(restaurantRepository.employeeExists(restaurantId, employeeId))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> restaurantService.addEmployeeById(restaurantId, employeeId));
        verify(restaurantRepository).employeeExists(restaurantId, employeeId);
        verify(restaurantRepository, never()).addEmployeeById(restaurantId, employeeId);
    }

    @Test
    void addNotExistingEmployeeById() {
        Long restaurantId = 1L;
        Long employeeId = 1L;

        when(restaurantRepository.employeeExists(restaurantId, employeeId))
                .thenReturn(false);

        restaurantService.addEmployeeById(restaurantId, employeeId);
        verify(restaurantRepository).employeeExists(restaurantId, employeeId);
        verify(restaurantRepository).addEmployeeById(restaurantId, employeeId);
    }

    @Test
    void deleteEmployeeById() {
        Long restaurantId = 1L;
        Long employeeId = 1L;
        restaurantService.deleteEmployeeById(restaurantId, employeeId);

        verify(restaurantRepository).deleteEmployeeById(restaurantId, employeeId);
    }

    @Test
    void addItemById() {
        Long restaurantId = 1L;
        Long itemId = 1L;
        restaurantService.addItemById(restaurantId, itemId);

        verify(restaurantRepository).addItemById(restaurantId, itemId);
    }

    @Test
    void deleteItemById() {
        Long restaurantId = 1L;
        Long itemId = 1L;
        restaurantService.deleteItemById(restaurantId, itemId);

        verify(restaurantRepository).deleteItemById(restaurantId, itemId);
    }

    @Test
    void addAddress() {
        Long restaurantId = 1L;
        Address address = new Address();

        doAnswer(invocation -> {
            Address address1 = invocation.getArgument(0);
            address1.setId(1L);
            return address1;
        }).when(addressService).create(address);

        restaurantService.addAddress(restaurantId, address);

        verify(addressService).create(address);
        verify(restaurantRepository).addAddressById(restaurantId, address.getId());
    }

    @Test
    void deleteAddressById() {
        Long restaurantId = 1L;
        Long addressId = 1L;
        restaurantService.deleteAddressById(restaurantId, addressId);

        verify(restaurantRepository).deleteAddressById(restaurantId, addressId);
    }

    @Test
    void delete() {
        Long id = 1L;
        restaurantService.delete(id);

        verify(restaurantRepository).delete(id);
    }
}