package com.switchfully.eurder.service.item.dto;

public class CreateItemDTO {
    private String name;
    private String description;
    private double price;
    private int stockCount;

    public CreateItemDTO setName(String name) {
        this.name = name;
        return this;
    }

    public CreateItemDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public CreateItemDTO setPrice(double price) {
        this.price = price;
        return this;
    }

    public CreateItemDTO setStockCount(int stockCount) {
        this.stockCount = stockCount;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStockCount() {
        return stockCount;
    }
}
