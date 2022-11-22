package com.switchfully.eurder.domain.customer;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.address.AddressBuilder;
import com.switchfully.eurder.domain.phonenumber.CountryCode;
import com.switchfully.eurder.domain.phonenumber.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CustomerRepository {
    private final Logger log = LoggerFactory.getLogger(CustomerRepository.class);
    private final Map<String, Customer> customerRepository;

    public CustomerRepository() {
        this.customerRepository = new HashMap<>();
//        fillCustomerRepository();
    }

    public Collection<Customer> getAllCustomers() {
        return customerRepository.values();
    }

    public void addCustomer(Customer customer) {
        customerRepository.put(customer.getCustomerID(), customer);
        log.info("Created ".concat(customer.toString()));
    }

    public Optional<Customer> getCustomerByEmail(String emailAddress) {
        return customerRepository.values().stream()
                .filter(customer -> customer.getEmailAddress().equals(emailAddress))
                .findFirst();
    }

    public Optional<Customer> findCustomerByID(String customerID) {
        return customerRepository.values().stream()
                .filter(customer -> customer.getCustomerID().equals(customerID))
                .findFirst();
    }

//    private void fillCustomerRepository() {
//        Customer admin = new CustomerBuilder()
//                .setLastname("admin")
//                .setEmailAddress("admin@eurder.com")
//                .setPassword("password")
//                .setAddress(new AddressBuilder()
//                        .setPostalCode("0000")
//                        .setStreetName("some street")
//                        .setStreetNumber("1")
//                        .setCityName("EURDER")
//                        .build())
//                .setRole(Role.ADMIN)
//                .build();
//        customerRepository.put(admin.getCustomerID(), admin);
//        Customer customer1 = new Customer("firstname1", "lastname1", "user1@test.be", new Address("street", "1", "1111", "city1"), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);
//        Customer customer2 = new Customer("firstname2", "lastname2", "user2@test.be", new Address("street", "1", "1111", "city2"), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);
//        Customer customer3 = new Customer("firstname3", "lastname3", "user3@test.be", new Address("street", "1", "1111", "city3"), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);
//        customerRepository.put(customer1.getCustomerID(), customer1);
//        customerRepository.put(customer2.getCustomerID(), customer2);
//        customerRepository.put(customer3.getCustomerID(), customer3);
//    }
}
