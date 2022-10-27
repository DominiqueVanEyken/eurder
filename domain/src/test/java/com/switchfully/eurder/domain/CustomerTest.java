package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void creatingCustomer() {
        String firstname = "firstname";
        String lastname = "lastname";
        String emailAddress = "user@test.be";
        Address address = new Address("street", 1, "3020", "city");
        String phoneNumber = "012 34 56 78";

        Customer customer = new Customer(firstname, lastname, emailAddress, address, phoneNumber);

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
        String firstname = "firstname";
        String lastname = "lastname";
        String emailAddress = "user@test.be";
        Address address = new Address("street", 1, "3020", "city");
        String phoneNumber = "012 34 56 78";

        Customer testCustomer = new Customer(firstname, lastname, emailAddress, address, phoneNumber);

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