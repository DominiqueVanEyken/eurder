package com.switchfully.eurder.service.order.dto;

public class ItemGroupReportDTO {
    private String name;
    private int amount;
    private String totalPrice;

    public String getName() {
        return name;
    }

    public ItemGroupReportDTO setName(String name) {
        this.name = name;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public ItemGroupReportDTO setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public ItemGroupReportDTO setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
}
