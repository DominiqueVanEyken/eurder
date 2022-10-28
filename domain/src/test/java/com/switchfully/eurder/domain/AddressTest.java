package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.address.AddressBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddressTest {

    @Nested
    class givenValidData {
        private final String streetName = "streetname";
        private final String streetNumber = "1";
        private final String postalCode = "3020";
        private final String city = "City";

        @Test
        void creatingAddress() {

            Address address = new Address(streetName, streetNumber, postalCode, city);

            assertThat(address).isNotNull();
            assertThat(address).isEqualTo(new Address(streetName, streetNumber, postalCode, city));
            assertThat(address.getFullAddressAsString()).isEqualTo(String.format("%s %s, %s %s", streetName, streetNumber, postalCode, city));
            assertThat(address.hashCode()).isEqualTo(new Address(streetName, streetNumber, postalCode, city).hashCode());
        }

        @Test
        void creatingAddressWithBuilderPattern() {
            Address testAddress = new Address(streetName, streetNumber, postalCode, city);

            Address addressBuilder = new AddressBuilder()
                    .setStreetName(streetName)
                    .setStreetNumber(streetNumber)
                    .setPostalCode(postalCode)
                    .setCityName(city)
                    .build();

            assertThat(addressBuilder).isNotNull();
            assertThat(addressBuilder).isEqualTo(testAddress);
            assertThat(addressBuilder.getFullAddressAsString()).isEqualTo(String.format("%s %s, %s %s", streetName, streetNumber, postalCode, city));
            assertThat(addressBuilder.hashCode()).isEqualTo(testAddress.hashCode());
        }
    }

    @Nested
    class givenInvalidData {
        @Test
        void postalCodeIsTooShortLongOrContainsLetters() {
            String errorMessage = "The provided postal code is not valid";

            assertThatThrownBy(() -> new Address(null, null, "123", "city"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
            assertThatThrownBy(() -> new Address(null, null, "12345", "city"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
            assertThatThrownBy(() -> new Address(null, null, "abcd", "city"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
        }

        @Test
        void cityIsNullOrLessThanTwoCharacters() {
            String errorMessage = "The provided city is not valid";

            assertThatThrownBy(() -> new Address(null, null, "1234", null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
            assertThatThrownBy(() -> new Address(null, null, "1234", "c"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
        }

        @Test
        void cityContainsOnlySpaces() {
            String errorMessage = "The provided city is not valid";

            assertThatThrownBy(() -> new Address(null, null, "1234", "   "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
        }
    }
}
