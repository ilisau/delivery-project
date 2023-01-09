package com.example.dp.web.controller;

import com.example.dp.domain.restaurant.Employee;
import com.example.dp.domain.restaurant.EmployeePosition;
import com.example.dp.service.EmployeeService;
import com.example.dp.web.dto.restaurant.CreateEmployeeDto;
import com.example.dp.web.dto.restaurant.EmployeeDto;
import com.example.dp.web.mapper.EmployeeMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/employees")
@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PutMapping
    public void save(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.INSTANCE.toEntity(employeeDto);
        employeeService.save(employee);
    }

    @PostMapping
    public EmployeeDto create(@Valid @RequestBody CreateEmployeeDto employeeDto) {
        Employee employee = employeeService.create(employeeDto);
        return EmployeeMapper.INSTANCE.toDto(employee);
    }

    @GetMapping("/by-restaurant/{id}")
    public List<EmployeeDto> getAllByRestaurantId(@PathVariable Long id,
                                                  @RequestParam(name = "position", required = false) EmployeePosition position) {
        List<Employee> employees;
        if(position != null) {
            employees = employeeService.getAllByRestaurantIdAndPosition(id, position);
        } else {
            employees = employeeService.getAllByRestaurantId(id);
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
        employeeService.deleteById(id);
    }
}
