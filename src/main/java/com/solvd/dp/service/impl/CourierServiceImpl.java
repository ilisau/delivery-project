package com.solvd.dp.service.impl;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.courier.CourierStatus;
import com.solvd.dp.domain.exception.ResourceAlreadyExistsException;
import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.user.Role;
import com.solvd.dp.repository.CourierRepository;
import com.solvd.dp.service.CourierService;
import com.solvd.dp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional(readOnly = true)
    public Courier getById(Long id) {
        return courierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Courier not found for this id :: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Courier getByEmail(String email) {
        return courierRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Courier not found for this email :: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public Courier getByOrderId(Long orderId) {
        return courierRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Courier not found for this order :: " + orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Courier> getAll() {
        return courierRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Courier> getAllByStatus(CourierStatus status) {
        return courierRepository.getAllByStatus(status);
    }

    @Override
    @Transactional
    public Courier update(Courier courier) {
        if (courierRepository.exists(courier)) {
            throw new ResourceAlreadyExistsException("Courier with such email or phone already exists :: " + courier);
        }
        courier.setPassword(passwordEncoder.encode(courier.getPassword()));
        courierRepository.update(courier);
        return courier;
    }

    @Override
    @Transactional
    public Courier create(Courier courier) {
        if (courierRepository.exists(courier)) {
            throw new ResourceAlreadyExistsException("Courier already exists :: " + courier);
        }
        if (!courier.getPassword().equals(courier.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation are not equal");
        }
        courier.setRoles(Set.of(Role.ROLE_COURIER));
        courier.setPassword(passwordEncoder.encode(courier.getPassword()));
        courier.setCreatedAt(LocalDateTime.now());
        courier.setLastActiveAt(LocalDateTime.now());
        courier.setStatus(CourierStatus.AVAILABLE);
        courierRepository.create(courier);
        courierRepository.saveRoles(courier.getId(), courier.getRoles());
        return courier;
    }

    @Override
    @Transactional
    public void assignOrder(Long courierId, Long orderId) {
        if (orderService.isOrderAssigned(orderId)) {
            throw new ResourceAlreadyExistsException("Order already assigned");
        }
        orderService.assignOrder(orderId, courierId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        courierRepository.delete(id);
    }

}
