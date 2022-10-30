package com.switchfully.eurder.domain.customer;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.customer.Role;
import com.switchfully.eurder.domain.phonenumber.CountryCode;
import com.switchfully.eurder.domain.phonenumber.PhoneNumber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

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
    void addCustomerToRepository() {
        int before = customerRepository.getAmountOfCustomersInRepository();
        customerRepository.addCustomer(testCustomer);
        int after = customerRepository.getAmountOfCustomersInRepository();

        assertThat(before).isLessThan(after);
    }

    @Test
    void addCustomerWithEmailThatAlreadyExists() {
        Customer duplicate = new Customer("firstname", "lastname", "user@test.be", new Address("street", "1", "1111", "city"), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);
        customerRepository.addCustomer(testCustomer);
        assertThatThrownBy(() -> customerRepository.addCustomer(duplicate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Customer already exists");
    }

    @Test
    void getCustomerByEmail_givenValidEmailAddress() {
        customerRepository.addCustomer(testCustomer);
        Customer result = customerRepository.getMemberByEmail("user@test.be");

        assertThat(result).isEqualTo(testCustomer);
    }

    @Test
    void getCustomerByEmail_givenInvalidEmailAddress() {
        assertThatThrownBy(() -> customerRepository.getMemberByEmail("user@test.be"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Wrong credentials");
    }

    @Test
    void getCustomerByID_givenValidID() {
        customerRepository.addCustomer(testCustomer);
        Customer result = customerRepository.findCustomerByID(testCustomer.getCustomerID());

        assertThat(result).isEqualTo(testCustomer);
    }

    @Test
    void getCustomerByID_givenInvalidID() {
        String invalidID = "invalidID";
        assertThatThrownBy(() -> customerRepository.findCustomerByID(invalidID))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Customer with ID " + invalidID + " does not exist");
    }

    @AfterEach
    void clear() {
//        customerRepository.clear();
    }
}