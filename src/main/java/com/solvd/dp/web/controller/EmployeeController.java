package com.solvd.dp.web.controller;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import com.solvd.dp.service.EmployeeService;
import com.solvd.dp.web.dto.OnCreate;
import com.solvd.dp.web.dto.OnUpdate;
import com.solvd.dp.web.dto.restaurant.EmployeeDto;
import com.solvd.dp.web.mapper.EmployeeMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/restaurants/{restaurantId}/employees")
@RestController
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    @PutMapping
    @Validated(OnUpdate.class)
    public void save(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.INSTANCE.toEntity(employeeDto);
        employeeService.save(employee);
    }

    @PostMapping
    @Validated(OnCreate.class)
    public EmployeeDto create(@PathVariable Long restaurantId,
                              @Valid @RequestBody EmployeeDto employeeDto) {
        Employee employeeToBeCreated = EmployeeMapper.INSTANCE.toEntity(employeeDto);
        Employee employee = employeeService.create(employeeToBeCreated, restaurantId);
        return EmployeeMapper.INSTANCE.toDto(employee);
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
        return employees.stream()
                .map(EmployeeMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeDto getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        return EmployeeMapper.INSTANCE.toDto(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        employeeService.delete(id);
    }

}
