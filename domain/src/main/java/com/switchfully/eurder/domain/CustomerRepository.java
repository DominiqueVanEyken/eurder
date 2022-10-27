package com.switchfully.eurder.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomerRepository {
    private final Map<String, Customer> customerRepository;

    public CustomerRepository() {
        this.customerRepository = new HashMap<>();
    }

    public void addCustomer(Customer customer) {
        customerRepository.put(customer.getCustomerID(), customer);
    }
}
