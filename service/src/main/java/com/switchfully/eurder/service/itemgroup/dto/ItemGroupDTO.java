package com.switchfully.eurder.service.itemgroup.dto;

import com.switchfully.eurder.domain.Price.Price;

import java.time.LocalDate;

public class ItemGroupDTO {
    private Long itemID;
    private String itemName;
    private int amount;
    private LocalDate shippingDate;
    private Price pricePerUnit;
    private Price totalPrice;

    public ItemGroupDTO setItemID(Long itemID) {
        this.itemID = itemID;
        return this;
    }

    public ItemGroupDTO setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public ItemGroupDTO setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemGroupDTO setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
        return this;
    }

    public ItemGroupDTO setPricePerUnit(Price pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        return this;
    }

    public ItemGroupDTO setTotalPrice(Price totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public Long getItemID() {
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

    public Price getPricePerUnit() {
        return pricePerUnit;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }
}
