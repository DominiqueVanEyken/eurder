package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Feature;
import com.switchfully.eurder.service.customer.CustomerService;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.customer.dto.CustomerDTO;
import com.switchfully.eurder.service.order.OrderService;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import com.switchfully.eurder.service.order.dto.report.ReportDTO;
import com.switchfully.eurder.service.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
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

    @GetMapping(value = "{customerID}/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportDTO getReport(@RequestHeader String authorization, @PathVariable String customerID) {
        securityService.validateAuthorization(authorization, Feature.VIEW_REPORT);
        customerService.validateIfCustomerIDExists(customerID);
        return orderService.getReportForCustomer(customerID);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
        log.debug("request for creation of new customer");
        return customerService.createNewCustomer(createCustomerDTO);
    }

    @PostMapping(value = "{customerID}/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO orderItems(@RequestHeader String authorization, @PathVariable String customerID, @RequestBody CreateOrderDTO createOrderDTO) {
        securityService.validateAuthorization(authorization, Feature.ORDER_ITEMS);
        customerService.validateIfCustomerIDExists(customerID);
        log.debug("Request for ordering items");
        return orderService.orderItems(customerID, createOrderDTO);
    }

    @PostMapping(value = "{customerID}/{orderID}/reorder", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO reOrderItemsByOrderID(@RequestHeader String authorization, @PathVariable String customerID, @PathVariable String orderID) {
        securityService.validateAuthorization(authorization, Feature.ORDER_ITEMS);
        log.debug("Requesting to reorder order with ID " + orderID);
        String username = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length()))).split(":")[0];
        return orderService.getOrderByID(customerID, orderID, username);
    }
}
