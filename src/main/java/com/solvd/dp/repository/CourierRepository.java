package com.solvd.dp.repository;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.courier.CourierStatus;
import com.solvd.dp.domain.user.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper
public interface CourierRepository {

    Optional<Courier> findById(Long id);

    Optional<Courier> findByEmail(String email);

    Optional<Courier> findByOrderId(Long orderId);

    List<Courier> getAll();

    List<Courier> getAllByStatus(CourierStatus status);

    void update(Courier courier);

    void create(Courier courier);

    boolean exists(Courier courier);

    void saveRoles(@Param("id") Long id, @Param("roles") Set<Role> roles);

    void delete(Long id);

}
