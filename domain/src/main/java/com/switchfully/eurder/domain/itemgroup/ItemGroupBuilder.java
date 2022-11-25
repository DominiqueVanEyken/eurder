package com.switchfully.eurder.domain.itemgroup;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.order.Order;

import java.time.LocalDate;

public class ItemGroupBuilder {
    private Order order;
    private Item item;
    private String itemName;
    private int amount;
    private LocalDate shippingDate;
    private Price pricePerUnit;

    public ItemGroupBuilder setOrder(Order order) {
        this.order = order;
        return this;
    }

    public ItemGroupBuilder setItem(Item item) {
        this.item = item;
        return this;
    }

    public ItemGroupBuilder setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public ItemGroupBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemGroupBuilder setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
        return this;
    }

    public ItemGroupBuilder setPricePerUnit(Price pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        return this;
    }

    public ItemGroup build() {
        return new ItemGroup(order, item, amount);
    }
}
