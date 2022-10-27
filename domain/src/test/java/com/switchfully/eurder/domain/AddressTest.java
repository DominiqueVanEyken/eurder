package com.switchfully.eurder.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressTest {

    @Test
    void creatingAddress() {
        String streetName = "streetname";
        int streetNumber = 1;
        String postalCode = "3020";
        String city = "City";
        Address address = new Address(streetName, streetNumber, postalCode, city);

        assertThat(address).isNotNull();
        assertThat(address).isEqualTo(new Address(streetName, streetNumber, postalCode, city));
        assertThat(address.getFullAddressAsString()).isEqualTo(String.format("%s %d, %s %s", streetName, streetNumber, postalCode, city));
        assertThat(address.hashCode()).isEqualTo(new Address(streetName, streetNumber, postalCode, city).hashCode());
    }

    @Test
    void creatingAddressWithBuilderPattern() {
        String streetName = "streetname";
        int streetNumber = 1;
        String postalCode = "3020";
        String city = "City";

        Address testAddress = new Address(streetName, streetNumber, postalCode, city);

        Address addressBuilder = new AddressBuilder()
                .setStreetName(streetName)
                .setStreetNumber(streetNumber)
                .setPostalCode(postalCode)
                .setCityName(city)
                .build();

        assertThat(addressBuilder).isNotNull();
        assertThat(addressBuilder).isEqualTo(testAddress);
        assertThat(addressBuilder.getFullAddressAsString()).isEqualTo(String.format("%s %d, %s %s", streetName, streetNumber, postalCode, city));
        assertThat(addressBuilder.hashCode()).isEqualTo(testAddress.hashCode());
    }
}