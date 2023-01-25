package com.solvd.dp.domain.user;

import com.solvd.dp.domain.restaurant.CartItem;
import com.solvd.dp.domain.restaurant.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class Cart {

    private Long id;
    private Map<Item, Long> items;
    private List<CartItem> cartItems;

    public Cart() {
        items = new HashMap<>();
        cartItems = new ArrayList<>();
    }

    public Map<Item, Long> getItems() {
        return cartItems.stream()
                .collect(Collectors.toMap(CartItem::getItem, CartItem::getQuantity));
    }
}
