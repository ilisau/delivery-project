package com.solvd.dp.repository;

import com.solvd.dp.domain.user.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface CartRepository {

    Optional<Cart> findById(Long id);

    Optional<Cart> findByUserId(Long userId);

    void create(Cart cart);

    void clear(Long id);

    void setByUserId(@Param("cartId") Long cartId, @Param("userId") Long userId);

    void addItemById(@Param("cartId") Long cartId, @Param("itemId") Long itemId, @Param("quantity") long quantity);

    void deleteItemById(@Param("cartId") Long cartId, @Param("itemId") Long itemId, @Param("quantity") long quantity);

    void delete(Long id);

}