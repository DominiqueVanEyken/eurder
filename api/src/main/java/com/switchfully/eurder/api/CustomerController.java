package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Feature;
import com.switchfully.eurder.service.customer.CustomerService;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.customer.dto.CustomerDTO;
import com.switchfully.eurder.service.order.OrderService;
import com.switchfully.eurder.service.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("customers")
public class CustomerController {

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;
    private final SecurityService securityService;
    private final OrderService orderService;

    public CustomerController(CustomerService customerService, SecurityService securityService, OrderService orderService) {
        this.customerService = customerService;
        this.securityService = securityService;
        this.orderService = orderService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<CustomerDTO> getAllCustomers(@RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Feature.GET_ALL_CUSTOMERS);
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "{customerID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDTO getCustomerByID(@RequestHeader String authorization, @PathVariable String customerID) {
        securityService.validateAuthorization(authorization, Feature.GET_CUSTOMER_DETAILS);
        return customerService.getCustomerByID(customerID);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
        log.debug("request for creation of new customer");
        return customerService.createNewCustomer(createCustomerDTO);
    }


}
