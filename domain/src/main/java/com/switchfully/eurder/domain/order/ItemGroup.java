package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

public class ItemGroup {
    private final String itemID;
    private final int amount;
    private final LocalDate shippingDate;
    private final Price pricePerUnit;
    private final Price totalPrice;
    @Autowired
    private ItemRepository itemRepostitory;

    public ItemGroup(String itemID, int amount) {
        this.itemID = itemID;
        this.amount = amount;
        shippingDate = setShippingDate(itemID);
        pricePerUnit = setPricePerUnit(itemID);
        totalPrice = new Price(calculateTotalPrice());
    }

    private Price setPricePerUnit(String itemID) {
        return itemRepostitory.getItemPriceByItemID(itemID);
    }

    public double calculateTotalPrice() {
        return pricePerUnit.getPrice() * amount;
    }

    private LocalDate setShippingDate(String itemID) {
        boolean itemIsInStock = itemRepostitory.isItemInStock(itemID);
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
