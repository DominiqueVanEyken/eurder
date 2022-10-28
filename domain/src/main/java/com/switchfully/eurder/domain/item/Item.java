package com.switchfully.eurder.domain.item;

public class Item {
    private final String name;
    private final String description;
    private final Price price;
    private final int stockCount;

    public Item(String name, String description, Price price, int stockCount) {
        this.name = validateName(name);
        this.description = description;
        this.price = validatePrice(price);
        this.stockCount = stockCount;
    }

    public String validateName(String name) {
        if (name == null || name.trim().length() < 2) {
            throw new IllegalArgumentException("The provided item name is not valid");
        }
        return name;
    }

    public Price validatePrice(Price price) {
        if (price == null || price.getPrice() <= 0) {
            throw new IllegalArgumentException("The provided price cannot be 0");
        }
        return price;
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
