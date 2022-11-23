package com.switchfully.eurder.domain.customer;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.address.PostalCode;
import com.switchfully.eurder.domain.phonenumber.PhoneNumber;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    private final String firstname = "firstname";
    private final String lastname = "lastname";
    private final String emailAddress = "user@test.be";
    private final Address address = new Address("street", "1", new PostalCode("3020", "city"));
    private final String countryCode = "+32";
    private final String localNumber = "012 34 56 78";
    private final PhoneNumber phoneNumber = new PhoneNumber(countryCode, localNumber);
    private final String password = "password";
    private Role role = Role.CUSTOMER;

    @Nested
    class givenValidData {
        @Test
        void creatingCustomer() {
            role = Role.CUSTOMER;
            Customer customer = new Customer(firstname, lastname, emailAddress, address, phoneNumber, password, role);

            assertThat(customer).isNotNull();
            assertThat(customer.getCustomerID()).isNotNull();
            assertThat(customer.getFirstname()).isEqualTo(firstname);
            assertThat(customer.getLastname()).isEqualTo(lastname);
            assertThat(customer.getEmailAddress()).isEqualTo(emailAddress);
            assertThat(customer.getFullAddress()).isEqualTo(address.toString());
            assertThat(customer.getPhoneNumber()).isEqualTo(phoneNumber.getPhoneNumber());
            assertThat(customer.doesPasswordMatch(password)).isTrue();
            assertThat(customer.getRole()).isEqualTo(role);
            assertThat(customer.canHaveAccessTo(Feature.CREATE_ITEM)).isFalse();
        }

        @Test
        void returningPhoneNumber_givenPhoneNumberIsNull() {
            Customer customer = new Customer(firstname, lastname, emailAddress, address, null, password, role);
            assertThat(customer.getPhoneNumber()).isEqualTo("");
        }

        @Test
        void returningPhoneNumber_givenAddressIsNull() {
            Customer customer = new Customer(firstname, lastname, emailAddress, null, phoneNumber, password, role);
            assertThat(customer.getFullAddress()).isEqualTo("");
        }

        @Test
        void creatingCustomerWithBuilderPattern() {
            Customer testCustomer = new Customer(firstname, lastname, emailAddress, address, phoneNumber, password, role);

            Customer customerBuilder = new CustomerBuilder()
                    .setFirstname(firstname)
                    .setLastname(lastname)
                    .setEmailAddress(emailAddress)
                    .setAddress(address)
                    .setPhoneNumber(phoneNumber)
                    .setPassword(password)
                    .setRole(role)
                    .build();

            assertThat(customerBuilder).isNotNull();
            assertThat(customerBuilder.getCustomerID()).isNotNull();
            assertThat(customerBuilder.getFirstname()).isEqualTo(testCustomer.getFirstname());
            assertThat(customerBuilder.getLastname()).isEqualTo(testCustomer.getLastname());
            assertThat(customerBuilder.getEmailAddress()).isEqualTo(testCustomer.getEmailAddress());
            assertThat(customerBuilder.getFullAddress()).isEqualTo(testCustomer.getFullAddress());
            assertThat(customerBuilder.getPhoneNumber()).isEqualTo(testCustomer.getPhoneNumber());
            assertThat(customerBuilder.doesPasswordMatch(password)).isTrue();
            assertThat(customerBuilder.getRole()).isEqualTo(role);
            assertThat(customerBuilder.canHaveAccessTo(Feature.CREATE_ITEM)).isFalse();
        }
    }

    @Nested
    class givenInvalidData {
        @Test
        void emailAddressIsNull() {
            assertThatThrownBy(() -> new Customer(firstname, lastname, null, address, phoneNumber, password, role))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("The provided email address is not valid");
        }

        @Test
        void emailAddressDoesNotContainTheCorrectFormat() {
            assertThatThrownBy(() -> new Customer(firstname, lastname, "emailAddress", address, phoneNumber, password, role))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("The provided email address is not valid");
        }

        @Test
        void lastNameIsNull() {
            assertThatThrownBy(() -> new Customer(firstname, null, emailAddress, address, phoneNumber, password, role))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("The provided name is not valid");
        }

        @Test
        void lastnameIsLessThanOneCharacter() {
            assertThatThrownBy(() -> new Customer(firstname, "", emailAddress, address, phoneNumber, password, role))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("The provided name is not valid");
        }

        @Test
        void passwordIsNullOrLessThanFiveCharacter() {
            assertThatThrownBy(() -> new Customer(firstname, lastname, emailAddress, address, phoneNumber, null, role))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("The provided password is not long enough");
        }

        @Test
        void passwordIsLessThanFiveCharacter() {
            assertThatThrownBy(() -> new Customer(firstname, lastname, emailAddress, address, phoneNumber, "1234", role))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("The provided password is not long enough");
        }
    }
}
