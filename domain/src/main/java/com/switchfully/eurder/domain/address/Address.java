package com.switchfully.eurder.domain.address;

import javax.persistence.*;
import java.util.Objects;
@Embeddable
public class Address {

    @Column(name = "STREET_NAME")
    private String streetName;
    @Column(name = "STREET_NUMBER")
    private String streetNumber;
    @ManyToOne
    @JoinColumn(name = "POSTAL_CODE")
    private PostalCode postalCode;

    public Address(String streetName, String streetNumber, PostalCode postalCode) {
        this.streetName = validateStringValueForNotNullOrEmpty(streetName);
        this.streetNumber = validateStringValueForNotNullOrEmpty(streetNumber);
        this.postalCode = postalCode;
    }

    public Address() {
    }

    public String validateStringValueForNotNullOrEmpty(String value) {
        if (value == null || value.trim().length() < 1) {
            throw new IllegalArgumentException("The provided street name or number is not valid");
        }
        return value;
    }

    public String getFullAddressAsString() {
        return String.format("%s %s, %s", streetName, streetNumber, postalCode.toString());
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(streetName, address.streetName) && Objects.equals(streetNumber, address.streetNumber) && Objects.equals(postalCode, address.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, streetNumber, postalCode);
    }
}
