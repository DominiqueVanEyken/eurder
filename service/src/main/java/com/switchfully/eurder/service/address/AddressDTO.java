package com.switchfully.eurder.service.address;

public class AddressDTO {
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;

    public AddressDTO setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public AddressDTO setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public AddressDTO setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public AddressDTO setCity(String city) {
        this.city = city;
        return this;
    }
}
