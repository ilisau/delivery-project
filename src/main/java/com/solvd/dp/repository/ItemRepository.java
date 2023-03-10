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

    Optional<Item> findById(Long id);

    Map<Item, Long> getAllByCartId(Long cartId);

    List<Item> getAllByType(ItemType type);

    List<Item> getAllByRestaurantId(Long restaurantId);

    List<Item> getAllByRestaurantIdAndType(@Param("restaurantId") Long restaurantId, @Param("type") ItemType type);

    void update(Item item);

    void create(Item item);

    Long getRestaurantIdByItemId(Long itemId);

    void delete(Long id);

}
