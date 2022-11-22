package com.switchfully.eurder.domain.Price;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.Objects;

@Embeddable
public class Price {
    @Column(name = "PRICE")
    private final double price;
    @Transient
    private final CurrencyUnit currencyUnit;

    public Price(double price) {
        this.price = price;
        currencyUnit = CurrencyUnit.EUR;
    }

    public Price() {
        this(0.0);
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", price, currencyUnit);
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
