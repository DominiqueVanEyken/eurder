package com.switchfully.eurder.domain.item;

import com.switchfully.eurder.domain.Price.Price;

public class ItemBuilder {
    private String name;
    private String description;
    private Price price;
    private int stockCount;

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ItemBuilder setPrice(Price price) {
        this.price = price;
        return this;
    }

    public ItemBuilder setStockCount(int stockCount) {
        this.stockCount = stockCount;
        return this;
    }

    public Item build() {
        return new Item(name, description, price, stockCount);
    }
}
