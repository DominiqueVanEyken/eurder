package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Feature;
import com.switchfully.eurder.service.customer.CustomerService;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.customer.dto.CustomerDTO;
import com.switchfully.eurder.service.order.OrderService;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import com.switchfully.eurder.service.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
        log.debug("request for creation of new customer");
        return customerService.createNewCustomer(createCustomerDTO);
    }

    @PostMapping(value = "{customerID}/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTO orderItems(@RequestHeader String authorization, @PathVariable String customerID, @RequestBody CreateOrderDTO createOrderDTO) {
        securityService.validateAuthorization(authorization, Feature.ORDER_ITEMS);
        log.debug("Request for ordering items");
        return orderService.orderItems(customerID, createOrderDTO);
    }

}
