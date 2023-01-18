package com.solvd.dp.domain.restaurant;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItem {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ItemType type;
    private Boolean available;
    private Long quantity;

    public Item getItem() {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setType(type);
        item.setAvailable(available);
        return item;
    }

}
