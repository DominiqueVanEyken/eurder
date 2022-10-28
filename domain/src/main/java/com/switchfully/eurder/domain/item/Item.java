package com.switchfully.eurder.domain.item;

public class Item {
    private final String name;
    private final String description;
    private final Price price;
    private final int stockCount;

    public Item(String name, String description, Price price, int stockCount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockCount = stockCount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPriceWithUnit() {
        return price.toString();
    }

    public int getStockCount() {
        return stockCount;
    }

    @Override
    public String toString() {
        return String.format("Item{name=%s, description=%s, price=%s, stockCount=%d", name, description, price, stockCount);
    }
}
