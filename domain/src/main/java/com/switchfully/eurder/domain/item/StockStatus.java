package com.switchfully.eurder.domain.item;

public enum StockStatus {
    STOCK_LOW(1, "low"),
    STOCK_MEDIUM(2, "medium"),
    STOCK_HIGH(3, "high");

    private final int rank;
    private final String status;

    StockStatus(int rank, String status) {
        this.rank = rank;
        this.status = status;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return status.toUpperCase();
    }
}
