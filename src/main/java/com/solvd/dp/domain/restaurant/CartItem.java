package com.solvd.dp.domain.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
