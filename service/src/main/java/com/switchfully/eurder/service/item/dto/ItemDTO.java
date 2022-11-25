package com.switchfully.eurder.service.item.dto;

public class ItemDTO {
    private long itemID;
    private String name;
    private String description;
    private String price;
    private String stockStatus;

    public long getItemID() {
        return itemID;
    }

    public ItemDTO setItemID(long itemID) {
        this.itemID = itemID;
        return this;
    }

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

    public ItemDTO setStockStatus(String stockCount) {
        this.stockStatus = stockCount;
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

    public String getStockStatus() {
        return stockStatus;
    }
}
