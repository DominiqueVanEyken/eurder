package com.switchfully.eurder.domain.Price;

public enum CurrencyUnit {
    EUR("euro", '€');
    private final String name;
    private final char symbol;

    CurrencyUnit(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }
}
