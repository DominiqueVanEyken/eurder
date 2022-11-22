package com.switchfully.eurder.domain.address;

public class AddressBuilder {
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String cityName;

    public AddressBuilder setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public AddressBuilder setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public AddressBuilder setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public AddressBuilder setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public Address build() {
        return new Address(streetName, streetNumber, new PostalCode(postalCode, cityName));
    }
}
