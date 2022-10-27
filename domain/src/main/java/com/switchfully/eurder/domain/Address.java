package com.switchfully.eurder.domain;

public class Address {

    private final String streetName;
    private final int streetNumber;
    private final String postalCode;
    private final String cityName;

    public Address(String streetName, int streetNumber, String postalCode, String cityName) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.cityName = cityName;
    }

    public String getFullAddressAsString() {
        return String.format("%s %d, %s %s", streetName, streetNumber, postalCode, cityName);
    }
}
