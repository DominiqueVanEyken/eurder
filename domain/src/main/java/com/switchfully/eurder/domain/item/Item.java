package com.switchfully.eurder.domain.item;

import com.switchfully.eurder.domain.Price.Price;

import java.time.LocalDate;

public class Item {
    public static final String ITEM_ID_PREFIX = "IID";
    private static int itemIDSuffix = 1001;
    private final String itemID;
    private final String name;
    private final String description;
    private final Price price;
    private int stockCount;

    public Item(String name, String description, Price price, int stockCount) {
        itemID = ITEM_ID_PREFIX + LocalDate.now().getYear() + itemIDSuffix++;
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

    public String getItemID() {
        return itemID;
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

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Item{itemID=%s, name=%s, description=%s, price=%s, stockCount=%d", itemID, name, description, price, stockCount);
    }

    public boolean isInStock(int amount) {
        return stockCount > amount;
    }

    public void reduceStockByAmount(int amount) {
        stockCount -= amount;
    }
}
