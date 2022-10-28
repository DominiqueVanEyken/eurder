package com.switchfully.eurder.domain.item;

public class Price {
    private final double price;
    private final CurrencyUnit currencyUnit;

    public Price(double price) {
        this.price = price;
        currencyUnit = CurrencyUnit.EUR;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", price, currencyUnit);
    }
}
