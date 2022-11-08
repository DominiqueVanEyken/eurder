package com.switchfully.eurder.service.customer;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.customer.Role;
import com.switchfully.eurder.domain.phonenumber.CountryCode;
import com.switchfully.eurder.domain.phonenumber.PhoneNumber;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerServiceTest {
    private CustomerRepository customerRepository;
    private CustomerService customerService;
    private final Customer testCustomer = new Customer("firstname", "lastname", "user@test.be", new Address("street", "1", "1111", "city"), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);

    @BeforeEach
    void createAndFillCustomerRepository() {
        customerRepository = new CustomerRepository();
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void addCustomerWithEmailThatAlreadyExists() {
        CreateCustomerDTO duplicate = new CreateCustomerDTO()
                .setFirstname(testCustomer.getFirstname())
                .setLastname(testCustomer.getLastname())
                .setEmailAddress(testCustomer.getEmailAddress())
                .setStreetName("Street")
                .setStreetNumber("1")
                .setPostalCode("1111")
                .setCityName("city")
                .setCountryCode(CountryCode.BEL.toString())
                .setLocalNumber("123 45 67 89")
                .setPassword("password");

        customerRepository.addCustomer(testCustomer);
        assertThatThrownBy(() -> customerService.createNewCustomer(duplicate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Customer already exists");
    }

    @Test
    void getCustomerByID_givenInvalidID() {
        String invalidID = "invalidID";
        assertThatThrownBy(() -> customerService.getCustomerByID(invalidID))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Customer with ID " + invalidID + " does not exist");
    }

}