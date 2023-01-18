package com.solvd.dp.repository;

import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.restaurant.ItemType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface ItemRepository {

    Optional<Item> findById(@Param("id") Long id);

    Map<Item, Long> getAllByCartId(@Param("cartId") Long cartId);

    List<Item> getAllByType(@Param("type") ItemType type);

    List<Item> getAllByRestaurantId(@Param("restaurantId") Long restaurantId);

    List<Item> getAllByRestaurantIdAndType(@Param("restaurantId") Long restaurantId, @Param("type") ItemType type);

    void update(@Param("item") Item item);

    void create(@Param("item") Item item);

    void delete(@Param("item") Long id);

}
