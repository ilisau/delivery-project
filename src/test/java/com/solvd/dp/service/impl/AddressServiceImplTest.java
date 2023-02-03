package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.user.Address;
import com.solvd.dp.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void getByExistingId() {
        Long id = 1L;
        Address address = new Address();
        address.setId(id);

        when(addressRepository.findById(id))
                .thenReturn(Optional.of(address));

        assertEquals(address, addressService.getById(id));
        verify(addressRepository).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;

        when(addressRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.getById(id));
        verify(addressRepository).findById(id);
    }

    @Test
    void getAllByUserId() {
        Long id = 1L;
        List<Address> addresses = new ArrayList<>();
        Stream.iterate(1, i -> i + 1)
                .limit(10)
                .forEach(i -> {
                    Address address = new Address();
                    address.setId((long) i);
                    addresses.add(address);
                });

        when(addressRepository.getAllByUserId(id))
                .thenReturn(addresses);

        assertEquals(addresses, addressService.getAllByUserId(id));
        verify(addressRepository).getAllByUserId(id);
    }

    @Test
    void getAllByRestaurantId() {
        Long id = 1L;
        List<Address> addresses = new ArrayList<>();
        Stream.iterate(1, i -> i + 1)
                .limit(5)
                .forEach(i -> {
                    Address address = new Address();
                    address.setId((long) i);
                    addresses.add(address);
                });

        when(addressRepository.getAllByRestaurantId(id))
                .thenReturn(addresses);

        when(addressRepository.getAllByRestaurantId(id))
                .thenReturn(addresses);

        assertEquals(addresses, addressService.getAllByRestaurantId(id));
        verify(addressRepository).getAllByRestaurantId(id);
    }

    @Test
    void update() {
        Address address = new Address();
        address.setId(1L);
        address.setStreetName("Mogilevskaya");
        address.setHouseNumber(1);

        doAnswer(invocation -> {
            Address address1 = invocation.getArgument(0);
            address1.setStreetName("Mogilevskaya");
            address1.setHouseNumber(1);
            return address1;
        }).when(addressRepository).update(address);

        addressService.update(address);

        assertEquals("Mogilevskaya", address.getStreetName());
        assertEquals(1, address.getHouseNumber());
        verify(addressRepository).update(address);
    }

    @Test
    void create() {
        Address address = new Address();
        address.setStreetName("Mogilevskaya");
        address.setHouseNumber(1);

        doAnswer(invocation -> {
            Address address1 = invocation.getArgument(0);
            address1.setId(1L);
            return address1;
        }).when(addressRepository).create(address);

        addressService.create(address);

        assertEquals(1L, address.getId());
        verify(addressRepository).create(address);
    }

    @Test
    void isUserOwner() {
        Long userId = 1L;
        Long addressId = 1L;

        when(addressRepository.isUserOwner(addressId, userId))
                .thenReturn(true);

        assertTrue(addressService.isUserOwner(addressId, userId));
        verify(addressRepository).isUserOwner(addressId, userId);
    }

    @Test
    void isEmployeeOwner() {
        Long employeeId = 1L;
        Long addressId = 1L;

        when(addressRepository.isEmployeeOwner(addressId, employeeId))
                .thenReturn(true);

        assertTrue(addressService.isEmployeeOwner(addressId, employeeId));
        verify(addressRepository).isEmployeeOwner(addressId, employeeId);
    }

    @Test
    void delete() {
        Long id = 1L;
        addressService.delete(id);

        verify(addressRepository).delete(id);
    }

}