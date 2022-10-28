package com.switchfully.eurder.service.item.dto;

public class ItemDTO {
    private String name;
    private String description;
    private String price;
    private int stockCount;

    public ItemDTO setName(String name) {
        this.name = name;
        return this;
    }

    public ItemDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public ItemDTO setPrice(String price) {
        this.price = price;
        return this;
    }

    public ItemDTO setStockCount(int stockCount) {
        this.stockCount = stockCount;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public int getStockCount() {
        return stockCount;
    }
}
