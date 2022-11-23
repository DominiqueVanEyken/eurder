package com.switchfully.eurder.domain.itemgroup;

import com.switchfully.eurder.domain.address.Address;

public class ItemGroupShippingReport {
    private final Address shippingAddress;
    private final ItemGroup itemGroup;

    public ItemGroupShippingReport(Address shippingAddress, ItemGroup itemGroup) {
        this.shippingAddress = shippingAddress;
        this.itemGroup = itemGroup;
    }

    public Address getShippingAddress() {
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
