package com.switchfully.eurder.domain.item;

public enum StockStatus {
    STOCK_LOW("low"),
    STOCK_MEDIUM("medium"),
    STOCK_HIGH("high");

    private final String status;

    StockStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status.toUpperCase();
    }
}
