package com.switchfully.eurder.domain.customer;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.phonenumber.CountryCode;
import com.switchfully.eurder.domain.phonenumber.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class CustomerRepositoryTest {
    private CustomerRepository customerRepository;
    private final Customer testCustomer = new Customer("firstname", "lastname", "user@test.be", new Address("street", "1", "1111", "city"), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);

    @BeforeEach
    void createAndFillCustomerRepository() {
        customerRepository = new CustomerRepository();
    }

    @Test
    void gettingAllCustomers() {
        Collection<Customer> customerList = customerRepository.getAllCustomers();

        assertThat(customerList).isNotNull();
        assertThat(customerList.size()).isGreaterThan(0);
    }

    @Test
    void addCustomerToRepository() {
        int before = customerRepository.getAllCustomers().size();
        customerRepository.addCustomer(testCustomer);
        int after = customerRepository.getAllCustomers().size();

        assertThat(before).isLessThan(after);
    }

    @Test
    void getCustomerByEmail_givenValidEmailAddress() {
        customerRepository.addCustomer(testCustomer);
        Optional<Customer> result = customerRepository.getCustomerByEmail("user@test.be");

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(testCustomer);
    }


    @Test
    void getCustomerByID_givenValidID() {
        customerRepository.addCustomer(testCustomer);
        Optional<Customer> result = customerRepository.findCustomerByID(testCustomer.getCustomerID());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(testCustomer);
    }

}