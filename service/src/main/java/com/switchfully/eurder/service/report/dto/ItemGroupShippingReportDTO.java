package com.switchfully.eurder.service.report.dto;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.address.Address;

public class ItemGroupShippingReportDTO {
    private long itemID;
    private String itemName;
    private int amount;
    private Price pricePerUnit;
    private Price totalPrice;
    private Address shippingAddress;

    public ItemGroupShippingReportDTO setItemID(long itemID) {
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

    public ItemGroupShippingReportDTO setPricePerUnit(Price pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        return this;
    }

    public ItemGroupShippingReportDTO setTotalPrice(Price totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public ItemGroupShippingReportDTO setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public long getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public int getAmount() {
        return amount;
    }

    public Price getPricePerUnit() {
        return pricePerUnit;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }
}
