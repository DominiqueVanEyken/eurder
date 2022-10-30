package com.switchfully.eurder.service.order.dto.itemgroup;

public class CreateItemGroupDTO {
    private String itemID;
    private int amount;

    public CreateItemGroupDTO setItemID(String itemID) {
        this.itemID = itemID;
        return this;
    }

    public CreateItemGroupDTO setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getItemID() {
        return itemID;
    }

    public int getAmount() {
        return amount;
    }
}
