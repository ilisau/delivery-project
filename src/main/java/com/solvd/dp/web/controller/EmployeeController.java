package com.solvd.dp.web.controller;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import com.solvd.dp.service.EmployeeService;
import com.solvd.dp.web.dto.restaurant.EmployeeDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import com.solvd.dp.web.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/restaurants/{restaurantId}/employees")
@RestController
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;

    @PutMapping
    @PreAuthorize("canAccessEmployee(#restaurantId, #employeeDto.id)")
    public void update(@PathVariable Long restaurantId,
                       @Validated(OnUpdate.class) @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        employeeService.update(employee);
    }

    @PostMapping
    @PreAuthorize("canCreateEmployee(#restaurantId)")
    public EmployeeDto create(@PathVariable Long restaurantId,
                              @Validated(OnCreate.class) @RequestBody EmployeeDto employeeDto) {
        Employee employeeToBeCreated = employeeMapper.toEntity(employeeDto);
        Employee employee = employeeService.create(employeeToBeCreated, restaurantId);
        return employeeMapper.toDto(employee);
    }

    @GetMapping
    @PreAuthorize("canAccessEmployees(#restaurantId)")
    public List<EmployeeDto> getAllByRestaurantId(@PathVariable Long restaurantId,
                                                  @RequestParam(required = false) EmployeePosition position) {
        List<Employee> employees;
        if (position != null) {
            employees = employeeService.getAllByRestaurantIdAndPosition(restaurantId, position);
        } else {
            employees = employeeService.getAllByRestaurantId(restaurantId);
        }
        return employeeMapper.toDto(employees);
    }

    @GetMapping("/{id}")
    @PreAuthorize("canAccessEmployee(#restaurantId, #id)")
    public EmployeeDto getById(@PathVariable Long restaurantId, @PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        return employeeMapper.toDto(employee);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("canAccessEmployee(#restaurantId, #id)")
    public void deleteById(@PathVariable Long restaurantId, @PathVariable Long id) {
        employeeService.delete(id);
    }

}
