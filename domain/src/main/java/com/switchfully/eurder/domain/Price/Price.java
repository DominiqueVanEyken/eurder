package com.switchfully.eurder.domain.Price;

import java.util.Objects;

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

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Double.compare(price1.price, price) == 0 && currencyUnit == price1.currencyUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, currencyUnit);
    }
}
