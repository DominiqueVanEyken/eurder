package com.switchfully.eurder.service.security;

import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.customer.Feature;
import com.switchfully.eurder.domain.customer.Role;
import com.switchfully.eurder.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
class SecurityServiceTest {
    @Autowired
    private CustomerRepository customerRepository;
    private SecurityService securityService;

    @BeforeEach
    void createAndFillCustomerRepository() {
        securityService = new SecurityService(customerRepository);
    }

    @Test
    void getCustomerByEmail_givenInvalidEmailAddress() {
        String authorization = Base64.getEncoder().encodeToString("invalid@email.be:password".getBytes());
        assertThatThrownBy(() -> securityService.validateAuthorization("Basic " + authorization, Feature.GET_ALL_CUSTOMERS))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Wrong credentials");
    }

}