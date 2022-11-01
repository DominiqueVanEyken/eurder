package com.switchfully.eurder.domain.order;

public class ShippingReport {
    private final String shippingAddress;
    private final ItemGroup itemGroup;

    public ShippingReport(String shippingAddress, ItemGroup itemGroup) {
        this.shippingAddress = shippingAddress;
        this.itemGroup = itemGroup;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getItemID() {
        return itemGroup.getItemID();
    }

    public String getItemName() {
        return itemGroup.getItemName();
    }

    public int getAmount() {
        return itemGroup.getAmount();
    }

    public String getPricePerUnit() {
        return itemGroup.getPricePerUnit();
    }

    public String getTotalPrice() {
        return itemGroup.getTotalPrice().toString();
    }
}
