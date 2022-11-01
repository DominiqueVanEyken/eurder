package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.Price.Price;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ItemGroup {
    private static final int MINIMUM_ORDER_AMOUNT_REQUIREMENT = 1;
    private final String itemID;
    private String itemName;
    private final int amount;
    private LocalDate shippingDate;
    private Price pricePerUnit;
    private Price totalPrice;

    public ItemGroup(String itemID, int amount) {
        this.itemID = validateItemID(itemID);
        this.amount = validateAmount(amount);
    }

    private int validateAmount(int amount) {
        if (amount < MINIMUM_ORDER_AMOUNT_REQUIREMENT) {
            throw new IllegalArgumentException("The minimum requirement to order is " + MINIMUM_ORDER_AMOUNT_REQUIREMENT);
        }
        return amount;
    }

    public void setShippingDateAndPrice(Item item) {
        itemName = item.getName();
        shippingDate = calculateShippingDate(item);
        pricePerUnit = item.getPrice();
        totalPrice = new Price(calculateTotalPrice());
    }

    public String validateItemID(String itemID) {
        if (itemID == null) {
            throw new IllegalArgumentException("The provided itemID is not valid");
        }
        boolean isValidItemID = Pattern.matches("IID[0-9]{8}", itemID);
        if (!isValidItemID) {
            throw new IllegalArgumentException("The provided itemID is not valid");
        }
        return itemID;
    }

    public double calculateTotalPrice() {
        return pricePerUnit.getPrice() * amount;
    }

    private LocalDate calculateShippingDate(Item item) {
        boolean itemIsInStock = item.isInStock(amount);
        if (itemIsInStock) {
            return LocalDate.now().plusDays(1);
        }
        return LocalDate.now().plusWeeks(1);
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public String getPricePerUnit() {
        return pricePerUnit.toString();
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public double getTotalPriceAsDouble() {
        return totalPrice.getPrice();
    }

    @Override
    public String toString() {
        return String.format("ItemGroup{itemID=%s, amount=%d", itemID, amount);
    }
}
