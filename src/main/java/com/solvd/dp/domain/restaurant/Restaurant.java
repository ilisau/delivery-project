package com.solvd.dp.domain.restaurant;

import com.solvd.dp.domain.user.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    private Long id;
    private String name;
    private String description;
    private List<Address> addresses;
    private List<Item> items;
    private List<Employee> employees;

}
