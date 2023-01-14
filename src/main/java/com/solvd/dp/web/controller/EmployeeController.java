package com.solvd.dp.web.controller;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import com.solvd.dp.service.EmployeeService;
import com.solvd.dp.web.dto.restaurant.EmployeeDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import com.solvd.dp.web.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
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
    public void update(@Validated(OnUpdate.class) @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        employeeService.update(employee);
    }

    @PostMapping
    public EmployeeDto create(@PathVariable Long restaurantId,
                              @Validated(OnCreate.class) @RequestBody EmployeeDto employeeDto) {
        Employee employeeToBeCreated = employeeMapper.toEntity(employeeDto);
        Employee employee = employeeService.create(employeeToBeCreated, restaurantId);
        return employeeMapper.toDto(employee);
    }

    @GetMapping
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
    public EmployeeDto getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        return employeeMapper.toDto(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        employeeService.delete(id);
    }

}
