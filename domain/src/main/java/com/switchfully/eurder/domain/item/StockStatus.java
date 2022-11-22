package com.switchfully.eurder.domain.item;

import java.util.NoSuchElementException;

public enum StockStatus {
    STOCK_LOW("low"),
    STOCK_MEDIUM("medium"),
    STOCK_HIGH("high");

    private final String status;

    StockStatus(String status) {
        this.status = status;
    }

    public static StockStatus findStockStatusByValue(String stockStatusValue) {
        for (StockStatus stockStatus : StockStatus.values()) {
            if (stockStatus.status.equals(stockStatusValue.toLowerCase())) {
                return stockStatus;
            }
        }
        throw new NoSuchElementException(String.format("Stock status with value %s could not be found.", stockStatusValue));
    }

    @Override
    public String toString() {
        return status.toUpperCase();
    }
}
