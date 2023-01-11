package com.solvd.dp.service;

import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.user.User;

public interface UserService {

    User getById(Long id) throws ResourceNotFoundException;

    User getByEmail(String email) throws ResourceNotFoundException;

    User getByPhoneNumber(String phoneNumber) throws ResourceNotFoundException;

    User save(User user);

    User create(User user);

    void addAddress(Long userId, Long addressId);

    void deleteAddress(Long userId, Long addressId);

    void deleteById(Long id);
}