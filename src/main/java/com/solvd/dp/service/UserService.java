package com.solvd.dp.service;

import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.user.User;

public interface UserService {

    User getById(Long id);

    User getByEmail(String email);

    User getByPhoneNumber(String phoneNumber);

    User save(User user);

    User create(User user);

    void addAddressById(Long userId, Long addressId);

    void deleteAddressById(Long userId, Long addressId);

    void delete(Long id);

}