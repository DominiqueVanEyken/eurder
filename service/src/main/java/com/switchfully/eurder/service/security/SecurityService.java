package com.switchfully.eurder.service.security;

import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.Feature;
import com.switchfully.eurder.domain.exceptions.UnauthorizedException;
import com.switchfully.eurder.domain.exceptions.WrongPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.NoSuchElementException;

@Service
public class SecurityService {
    private final Logger log = LoggerFactory.getLogger(SecurityService.class);
    private final CustomerRepository customerRepository;

    public SecurityService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void validateAuthorization(String authorization, Feature feature) {
        UsernamePassword usernamePassword = getUsernamePassword(authorization);
        Customer user = customerRepository.getCustomerByEmail(usernamePassword.getUsername());
        if (!user.doesPasswordMatch(usernamePassword.getPassword())) {
            log.error("Password does not match for user " + usernamePassword.getUsername());
            throw new WrongPasswordException();
        }
        if (!user.canHaveAccessTo(feature)) {
            log.error("User " + usernamePassword.getUsername() + " does not have access to " + feature);
            throw new UnauthorizedException();
        }
    }

    private UsernamePassword getUsernamePassword(String authorization) {
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String username = decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
        String password = decodedUsernameAndPassword.substring(decodedUsernameAndPassword.indexOf(":") + 1);
        return new UsernamePassword(username, password);
    }
}
