package com.switchfully.eurder.service.item.dto;

public class UpdateItemDTO {
    private String name;
    private String description;
    private double price;
    private int stockCount;

    public String getName() {
        return name;
    }

    public UpdateItemDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UpdateItemDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public UpdateItemDTO setPrice(double price) {
        this.price = price;
        return this;
    }

    public int getStockCount() {
        return stockCount;
    }

    public UpdateItemDTO setStockCount(int stockCount) {
        this.stockCount = stockCount;
        return this;
    }
}
