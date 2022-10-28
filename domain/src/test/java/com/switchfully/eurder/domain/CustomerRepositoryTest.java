package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.customer.Role;
import com.switchfully.eurder.domain.phonenumber.CountryCode;
import com.switchfully.eurder.domain.phonenumber.PhoneNumber;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerRepositoryTest {

    private final Customer customer = new Customer("firstname", "lastname", "user@test.be", new Address("street", 1,"1111", "city"), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);

    @Test
    void addCustomerToRepository() {
        CustomerRepository customerRepository = new CustomerRepository();
        int before = customerRepository.getAmountOfCustomersInRepository();
        customerRepository.addCustomer(customer);
        int after = customerRepository.getAmountOfCustomersInRepository();

        assertThat(before).isLessThan(after);
    }
}