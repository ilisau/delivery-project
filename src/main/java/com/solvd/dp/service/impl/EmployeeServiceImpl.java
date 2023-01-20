package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceAlreadyExistsException;
import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import com.solvd.dp.domain.user.Role;
import com.solvd.dp.repository.EmployeeRepository;
import com.solvd.dp.service.EmployeeService;
import com.solvd.dp.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RestaurantService restaurantService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional(readOnly = true)
    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this username :: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllByRestaurantId(Long restaurantId) {
        return employeeRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllByRestaurantIdAndPosition(Long restaurantId, EmployeePosition position) {
        return employeeRepository.getAllByRestaurantIdAndPosition(restaurantId, position);
    }

    @Override
    @Transactional
    public Employee update(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeRepository.update(employee);
        return employee;
    }

    @Override
    @Transactional
    public Employee create(Employee employee, Long restaurantId) throws ResourceNotFoundException {
        if (employeeRepository.exists(employee, restaurantId)) {
            throw new ResourceAlreadyExistsException("Employee already exists :: " + employee);
        }
        if (!employee.getPassword().equals(employee.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation are not equal");
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRoles(Set.of(Role.ROLE_EMPLOYEE));
        employeeRepository.create(employee);
        employeeRepository.saveRoles(employee.getId(), employee.getRoles());
        restaurantService.addEmployeeById(restaurantId, employee.getId());
        return employee;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long employeeId, Long restaurantId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        return employeeRepository.exists(employee, restaurantId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isManager(Long employeeId, Long restaurantId) {
        return employeeRepository.isManager(employeeId, restaurantId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        employeeRepository.delete(id);
    }

}
