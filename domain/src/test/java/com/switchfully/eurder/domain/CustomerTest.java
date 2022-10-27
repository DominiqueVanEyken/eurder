package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    private final String firstname = "firstname";
    private final String lastname = "lastname";
    private final String emailAddress = "user@test.be";
    private final Address address = new Address("street", 1, "3020", "city");
    private final String phoneNumber = "012 34 56 78";
    private final String password = "password";

    @Test
    void creatingCustomer() {
        Customer customer = new Customer(firstname, lastname, emailAddress, address, phoneNumber, password);

        assertThat(customer).isNotNull();
        assertThat(customer.getCustomerID()).isNotNull();
        assertThat(customer.getFirstname()).isEqualTo(firstname);
        assertThat(customer.getLastname()).isEqualTo(lastname);
        assertThat(customer.getEmailAddress()).isEqualTo(emailAddress);
        assertThat(customer.getFullAddress()).isEqualTo(address.getFullAddressAsString());
        assertThat(customer.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    void creatingCustomerWithBuilderPattern() {
        Customer testCustomer = new Customer(firstname, lastname, emailAddress, address, phoneNumber, password);

        Customer customerBuilder = new CustomerBuilder()
                .setFirstname(firstname)
                .setLastname(lastname)
                .setEmailAddress(emailAddress)
                .setAddress(address)
                .setPhoneNumber(phoneNumber)
                .build();

        assertThat(customerBuilder).isNotNull();
        assertThat(customerBuilder.getCustomerID()).isNotNull();
        assertThat(customerBuilder.getFirstname()).isEqualTo(testCustomer.getFirstname());
        assertThat(customerBuilder.getLastname()).isEqualTo(testCustomer.getLastname());
        assertThat(customerBuilder.getEmailAddress()).isEqualTo(testCustomer.getEmailAddress());
        assertThat(customerBuilder.getFullAddress()).isEqualTo(testCustomer.getFullAddress());
        assertThat(customerBuilder.getPhoneNumber()).isEqualTo(testCustomer.getPhoneNumber());
    }
}