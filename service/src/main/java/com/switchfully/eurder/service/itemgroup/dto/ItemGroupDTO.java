package com.switchfully.eurder.service.itemgroup.dto;

import java.time.LocalDate;

public class ItemGroupDTO {
    private Long itemID;
    private String itemName;
    private int amount;
    private LocalDate shippingDate;
    private String pricePerUnit;

    private String totalPrice;

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

    public ItemGroupDTO setPricePerUnit(String pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        return this;
    }

    public ItemGroupDTO setTotalPrice(String totalPrice) {
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

    public String getPricePerUnit() {
        return pricePerUnit;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
}
