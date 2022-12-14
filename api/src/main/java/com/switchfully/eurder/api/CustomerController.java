package com.switchfully.eurder.api;

import com.switchfully.eurder.service.customer.CustomerService;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.customer.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("customers")
public class CustomerController {

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('GET_ALL_CUSTOMERS')")
    public Collection<CustomerDTO> getAllCustomers() {
        return customerService.RequestAllCustomers();
    }

    @GetMapping(value = "{customerID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('GET_CUSTOMER_DETAILS')")
    public CustomerDTO getCustomerByID(@PathVariable String customerID) {
        return customerService.RequestCustomerByID(customerID);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
        log.debug("request for creation of new customer");
        return customerService.createNewCustomer(createCustomerDTO);
    }


}
