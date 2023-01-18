package com.solvd.dp.repository;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.courier.CourierStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CourierRepository {

    Optional<Courier> findById(@Param("id") Long id);

    Optional<Courier> findByOrderId(@Param("orderId") Long orderId);

    List<Courier> getAll();

    List<Courier> getAllByStatus(@Param("status") CourierStatus status);

    void update(@Param("courier") Courier courier);

    void create(@Param("courier") Courier courier);

    boolean exists(@Param("courier") Courier courier);

    void delete(@Param("id") Long id);

}
