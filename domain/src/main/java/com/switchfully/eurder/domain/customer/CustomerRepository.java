package com.switchfully.eurder.domain.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByEmailAddress(String email);

//    private void fillCustomerRepository() {
//        Customer customer1 = new Customer("firstname1", "lastname1", "user1@test.be", new Address("street", "1", "1111", "city1"), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);
//        Customer customer2 = new Customer("firstname2", "lastname2", "user2@test.be", new Address("street", "1", "1111", "city2"), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);
//        Customer customer3 = new Customer("firstname3", "lastname3", "user3@test.be", new Address("street", "1", "1111", "city3"), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);
//        customerRepository.put(customer1.getCustomerID(), customer1);
//        customerRepository.put(customer2.getCustomerID(), customer2);
//        customerRepository.put(customer3.getCustomerID(), customer3);
//    }
}
