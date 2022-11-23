package com.switchfully.eurder.service.report.dto;

import com.switchfully.eurder.domain.address.Address;

public class ItemGroupShippingReportDTO {
    private String itemID;
    private String itemName;
    private int amount;
    private String pricePerUnit;
    private String totalPrice;
    private Address shippingAddress;

    public ItemGroupShippingReportDTO setItemID(String itemID) {
        this.itemID = itemID;
        return this;
    }

    public ItemGroupShippingReportDTO setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public ItemGroupShippingReportDTO setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemGroupShippingReportDTO setPricePerUnit(String pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        return this;
    }

    public ItemGroupShippingReportDTO setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public ItemGroupShippingReportDTO setShippingAddress(Address shippingAddress) {
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

    public Address getShippingAddress() {
        return shippingAddress;
    }
}
