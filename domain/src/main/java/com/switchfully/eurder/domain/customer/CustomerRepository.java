package com.switchfully.eurder.domain.customer;

import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerBuilder;
import com.switchfully.eurder.domain.customer.Role;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class CustomerRepository {
    private final Map<String, Customer> customerRepository;

    public CustomerRepository() {
        this.customerRepository = new HashMap<>();
        Customer admin = new CustomerBuilder()
                .setFirstname("admin")
                .setEmailAddress("admin@eurder.com")
                .setPassword("password")
                .setRole(Role.ADMIN)
                .build();
        customerRepository.put(admin.getCustomerID(), admin);
    }

    public void addCustomer(Customer customer) {
        customerRepository.put(customer.getCustomerID(), customer);
    }

    public int getAmountOfCustomersInRepository() {
        return customerRepository.values().size();
    }

    public Customer getMemberByEmail(String emailAddress) {
        for (Customer customer : customerRepository.values()) {
            if (customer.getEmailAddress().equals(emailAddress)) {
                return customer;
            }
        }
        throw new NoSuchElementException("Wrong credentials");
    }
}
