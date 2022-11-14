package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.Price.Price;

import java.time.LocalDate;

public class ItemGroupBuilder {
    private String itemID;
    private String itemName;
    private int amount;
    private LocalDate shippingDate;
    private Price pricePerUnit;

    public ItemGroupBuilder setItemID(String itemID) {
        this.itemID = itemID;
        return this;
    }

    public ItemGroupBuilder setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public ItemGroupBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemGroupBuilder setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
        return this;
    }

    public ItemGroupBuilder setPricePerUnit(Price pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        return this;
    }

    public ItemGroup build() {
        return new ItemGroup(itemID, itemName, amount, shippingDate, pricePerUnit);
    }
}
