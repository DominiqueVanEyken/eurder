package com.switchfully.eurder.service.order.dto;

public class CreateItemGroupDTO {
    private long itemID;
    private int amount;

    public CreateItemGroupDTO setItemID(long itemID) {
        this.itemID = itemID;
        return this;
    }

    public CreateItemGroupDTO setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public long getItemID() {
        return itemID;
    }

    public int getAmount() {
        return amount;
    }
}
