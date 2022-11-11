package com.switchfully.eurder.domain.item;

import com.switchfully.eurder.domain.Price.Price;

import java.time.LocalDate;

public class Item implements Comparable<Item> {
    public static final String ITEM_ID_PREFIX = "IID";
    private static int itemIDSuffix = 1001;
    private final String itemID;
    private String name;
    private String description;
    private Price price;
    private int stockCount;
    private StockStatus stockStatus;

    public Item(String name, String description, Price price, int stockCount) {
        itemID = ITEM_ID_PREFIX + LocalDate.now().getYear() + itemIDSuffix++;
        this.name = validateName(name);
        this.description = description;
        this.price = validatePrice(price);
        this.stockCount = stockCount;
        setStockStatus();
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

    public boolean isItemInStock(int amount) {
        return stockCount > amount;
    }

    public void reduceStockByAmount(int amount) {
        stockCount -= amount;
        setStockStatus();
    }

    private void setStockStatus() {
        if (stockCount < 5) {
            stockStatus = StockStatus.STOCK_LOW;
        } else if (stockCount < 10) {
            stockStatus = StockStatus.STOCK_MEDIUM;
        } else {
            stockStatus = StockStatus.STOCK_HIGH;
        }
    }

    public void updateItem(String name, String description, double price, int stockCount) {
        this.name = validateName(name);
        this.description = description;
        this.price = validatePrice(new Price(price));
        this.stockCount = stockCount;
        setStockStatus();
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

    public String getStockStatus() {
        return stockStatus.toString();
    }

    @Override
    public String toString() {
        return String.format("Item{itemID=%s, name=%s, description=%s, price=%s, stockCount=%d", itemID, name, description, price, stockCount);
    }

    @Override
    public int compareTo(Item other) {
        return stockCount - other.getStockCount();
    }

    public LocalDate getShippingDateForAmount(int amount) {
        if (isItemInStock(amount)) {
            return LocalDate.now().plusDays(1);
        }
        return LocalDate.now().plusWeeks(1);
    }
}
