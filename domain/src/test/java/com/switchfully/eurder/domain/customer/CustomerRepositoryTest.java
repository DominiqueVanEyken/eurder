package com.switchfully.eurder.domain.customer;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.address.PostalCode;
import com.switchfully.eurder.domain.phonenumber.CountryCode;
import com.switchfully.eurder.domain.phonenumber.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    private final Customer testCustomer = new Customer("firstname", "lastname", "user@test.be", new Address("street", "1", new PostalCode("1111", "city")), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);

    @Test
    void gettingAllCustomers() {
        Collection<Customer> customerList = customerRepository.findAll();

        assertThat(customerList).isNotNull();
        assertThat(customerList.size()).isGreaterThan(0);
    }

    @Test
    void addCustomerToRepository() {
        int before = customerRepository.findAll().size();
        customerRepository.save(testCustomer);
        int after = customerRepository.findAll().size();

        assertThat(before).isLessThan(after);
    }

    @Test
    void getCustomerByEmail_givenValidEmailAddress() {
        customerRepository.save(testCustomer);
        Optional<Customer> result = customerRepository.findByEmailAddress("user@test.be");

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(testCustomer);
    }


    @Test
    void getCustomerByID_givenValidID() {
        customerRepository.save(testCustomer);
        Optional<Customer> result = customerRepository.findById(testCustomer.getCustomerID());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(testCustomer);
    }

}