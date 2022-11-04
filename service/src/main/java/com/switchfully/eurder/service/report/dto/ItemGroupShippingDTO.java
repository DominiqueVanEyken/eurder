package com.switchfully.eurder.service.report.dto;

public class ItemGroupShippingDTO {
    private String itemID;
    private String itemName;
    private int amount;
    private String pricePerUnit;
    private String totalPrice;
    private String shippingAddress;

    public ItemGroupShippingDTO setItemID(String itemID) {
        this.itemID = itemID;
        return this;
    }

    public ItemGroupShippingDTO setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public ItemGroupShippingDTO setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemGroupShippingDTO setPricePerUnit(String pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        return this;
    }

    public ItemGroupShippingDTO setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public ItemGroupShippingDTO setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
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

    public String getPricePerUnit() {
        return pricePerUnit;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }
}
