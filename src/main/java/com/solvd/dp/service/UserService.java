package com.solvd.dp.service;

import com.solvd.dp.domain.user.Address;
import com.solvd.dp.domain.user.User;

public interface UserService {

    User getById(Long id);

    User getByEmail(String email);

    User getByPhoneNumber(String phoneNumber);

    User update(User user);

    User create(User user);

    void addAddress(Long userId, Address address);

    void deleteAddressById(Long userId, Long addressId);

    void delete(Long id);

}