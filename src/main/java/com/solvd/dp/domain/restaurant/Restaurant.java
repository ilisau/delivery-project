package com.solvd.dp.domain.restaurant;

import com.solvd.dp.domain.user.Address;
import lombok.Data;

import java.util.List;

@Data
public class Restaurant {

    private Long id;
    private String name;
    private String description;
    private List<Address> addresses;
    private List<Item> items;
    private List<Employee> employees;

}
