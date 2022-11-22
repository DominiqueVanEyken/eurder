package com.switchfully.eurder.domain.address;

import java.util.Objects;
import java.util.regex.Pattern;

public class Address {

    private final String streetName;
    private final String streetNumber;
    private final String postalCode;
    private final String cityName;

    public Address(String streetName, String streetNumber, String postalCode, String cityName) {
        this.streetName = validateStringValueForNotNullOrEmpty(streetName);
        this.streetNumber = validateStringValueForNotNullOrEmpty(streetNumber);
        this.postalCode = validatePostalCode(postalCode);
        this.cityName = validateStringValueForNotNullOrEmpty(cityName);
    }

    public String validatePostalCode(String postalCode) {
        boolean isValidPostalCode = Pattern.matches("[0-9]{4}", postalCode);
        if (!isValidPostalCode) {
            throw new IllegalArgumentException("The provided postal code is not valid");
        }
        return postalCode;
    }

    public String validateStringValueForNotNullOrEmpty(String value) {
        if (value == null || value.trim().length() < 2) {
            throw new IllegalArgumentException("The provided city, street name or number is not valid");
        }
        return value;
    }

    public String getFullAddressAsString() {
        return String.format("%s %s, %s %s", streetName, streetNumber, postalCode, cityName);
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
