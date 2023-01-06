package com.example.dp.web.controller.courier;

import com.example.dp.service.CourierService;
import com.example.dp.web.dto.courier.CourierDto;
import com.example.dp.web.dto.mapper.CourierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.dp.web.controller.courier.CourierURLs.BASE_URL;
import static com.example.dp.web.controller.courier.CourierURLs.ID_URL;

@RequestMapping(BASE_URL)
@RestController
public class CourierController {

    private final CourierService courierService;

    @Autowired
    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @GetMapping(ID_URL)
    public CourierDto getCourierById(@PathVariable Long id) {
        return CourierMapper.INSTANCE.toDto(courierService.getById(id));
    }
}
