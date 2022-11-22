package com.switchfully.eurder.domain.phonenumber;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PhoneNumberTest {

    @Nested
    class givenValidData {

        @Test
        void withNineCipherLocalNumber() {
            PhoneNumber phoneNumber = new PhoneNumber(CountryCode.BEL, "123456789");
            assertThat(phoneNumber.getPhoneNumber()).isEqualTo("+32 123 45 67 89");
        }

        @Test
        void withEightCipherLocalNumber() {
            PhoneNumber phoneNumber = new PhoneNumber(CountryCode.BEL, "12345678");
            assertThat(phoneNumber.getPhoneNumber()).isEqualTo("+32 12 34 56 78");
        }

        @Test
        void withLocalNumberWithLeadingTrailingAndSeparatingSpaces() {
            PhoneNumber phoneNumber = new PhoneNumber(CountryCode.BEL, "123456789");
            assertThat(phoneNumber.getPhoneNumber()).isEqualTo("+32 123 45 67 89");
        }

        @Test
        void withLocalNumberWithLeadingZero() {
            PhoneNumber phoneNumber = new PhoneNumber("+32", "0123456789");
            assertThat(phoneNumber.getPhoneNumber()).isEqualTo("+32 123 45 67 89");
        }

        @Test
        void withCountryCodeAsString() {
            PhoneNumber phoneNumber = new PhoneNumber("+32", "123456789");
            assertThat(phoneNumber.getPhoneNumber()).isEqualTo("+32 123 45 67 89");
        }

        @Test
        void countryCodeAsStringEqualsCountryCodeNotAsString() {
            PhoneNumber countryCodeAsString = new PhoneNumber("+32", "123456789");
            PhoneNumber countryCodeNotAsString = new PhoneNumber(CountryCode.BEL, "123456789");
            assertThat(countryCodeAsString.equals(countryCodeNotAsString)).isTrue();
            assertThat(countryCodeAsString.hashCode()).isEqualTo(countryCodeNotAsString.hashCode());
        }

    }

    @Nested
    class givenInvalidData {

        @Test
        void localNumberIsToshortLongOrContainsLetters() {
            String errorMessage = "The provided phone number is not valid";

            assertThatThrownBy(() -> new PhoneNumber(CountryCode.BEL, "1234567"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
            assertThatThrownBy(() -> new PhoneNumber(CountryCode.BEL, "1234567890"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
            assertThatThrownBy(() -> new PhoneNumber(CountryCode.BEL, "abc456789"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
        }

        @Test
        void countryCodeIsNotFound() {
            String errorMessage = "The provided country code could not be found";

            assertThatThrownBy(() -> new PhoneNumber("+00", "12345678"))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessageContaining(errorMessage);
        }

    }
}
