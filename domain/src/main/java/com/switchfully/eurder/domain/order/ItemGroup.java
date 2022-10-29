package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.Price;

import java.time.LocalDate;

public class ItemGroup {
    private final String itemID;
    private final int amount;
    private LocalDate shippingDate;
    private Price pricePerUnit;
    private Price totalPrice;

    public ItemGroup(String itemID, int amount) {
        this.itemID = itemID;
        this.amount = amount;
    }

    public void setShippingDateAndPrice(Item item) {
        shippingDate = setShippingDate(item);
        pricePerUnit = setPricePerUnit(item);
        totalPrice = new Price(calculateTotalPrice());
    }

    private Price setPricePerUnit(Item item) {
        return item.getPrice();
    }

    public double calculateTotalPrice() {
        return pricePerUnit.getPrice() * amount;
    }

    private LocalDate setShippingDate(Item item) {
        boolean itemIsInStock = item.isInStock(amount);
        if (itemIsInStock) {
            return LocalDate.now().plusDays(1);
        }
        return LocalDate.now().plusWeeks(1);
    }

    public String getItemID() {
        return itemID;
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
