package com.switchfully.eurder.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return streetNumber == address.streetNumber && Objects.equals(streetName, address.streetName) && Objects.equals(postalCode, address.postalCode) && Objects.equals(cityName, address.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, streetNumber, postalCode, cityName);
    }
}
