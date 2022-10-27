package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.customer.Customer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerRepositoryTest {

    private final Customer customer = new Customer("firstname", "lastname", "user@test.be", new Address("street", 1,"1111", "city"), "012 34 56 78");

    @Test
    void addCustomerToRepository() {
        CustomerRepository customerRepository = new CustomerRepository();
        int before = customerRepository.getAmountOfCustomersInRepository();
        customerRepository.addCustomer(customer);
        int after = customerRepository.getAmountOfCustomersInRepository();

        assertThat(before).isLessThan(after);
    }
}