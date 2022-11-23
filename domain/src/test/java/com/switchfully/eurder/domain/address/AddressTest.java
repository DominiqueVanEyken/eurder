package com.switchfully.eurder.domain.address;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddressTest {

    @Nested
    class givenValidData {
        private final String streetName = "streetname";
        private final String streetNumber = "1";
        private final String zipcode = "3020";
        private final String city = "City";
        private final PostalCode postalCode = new PostalCode(zipcode, city);

        @Test
        void creatingAddress() {

            Address address = new Address(streetName, streetNumber, postalCode);

            assertThat(address).isNotNull();
            assertThat(address).isEqualTo(new Address(streetName, streetNumber, postalCode));
            assertThat(address.toString()).isEqualTo(String.format("%s %s, %s", streetName, streetNumber, postalCode));
            assertThat(address.hashCode()).isEqualTo(new Address(streetName, streetNumber, postalCode).hashCode());
        }

        @Test
        void creatingAddressWithBuilderPattern() {
            Address testAddress = new Address(streetName, streetNumber, postalCode);

            Address addressBuilder = new AddressBuilder()
                    .setStreetName(streetName)
                    .setStreetNumber(streetNumber)
                    .setPostalCode(zipcode)
                    .setCityName(city)
                    .build();

            assertThat(addressBuilder).isNotNull();
            assertThat(addressBuilder.toString()).isEqualTo(String.format("%s %s, %s %s", streetName, streetNumber, zipcode, city));
        }
    }

    @Nested
    class givenInvalidData {
        @Test
        void postalCodeIsTooShortLongOrContainsLetters() {
            String errorMessage = "The provided postal code is not valid";

            assertThatThrownBy(() -> new Address(null, null, new PostalCode("123", "city")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
            assertThatThrownBy(() -> new Address(null, null, new PostalCode("12345", "city")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
            assertThatThrownBy(() -> new Address(null, null, new PostalCode("abcd", "city")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
        }

        @Test
        void cityIsNullOrLessThanTwoCharacters() {
            String errorMessage = "The provided city is not valid";

            assertThatThrownBy(() -> new Address(null, null, new PostalCode("1234", null)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
            assertThatThrownBy(() -> new Address(null, null, new PostalCode("1234", "c")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
        }

        @Test
        void cityContainsOnlySpaces() {
            String errorMessage = "The provided city is not valid";

            assertThatThrownBy(() -> new Address(null, null, new PostalCode("1234", "   ")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
        }
    }
}
