package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Feature;
import com.switchfully.eurder.service.customer.CustomerService;
import com.switchfully.eurder.service.order.OrderService;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import com.switchfully.eurder.service.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping
public class OrderController {
    private final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final CustomerService customerService;
    private final SecurityService securityService;

    public OrderController(OrderService orderService, CustomerService customerService, SecurityService securityService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.securityService = securityService;
    }

    @PostMapping(value = "customers/{customerID}/orders/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO orderItems(@RequestHeader String authorization, @PathVariable String customerID, @RequestBody CreateOrderDTO createOrderDTO) {
        securityService.validateAuthorization(authorization, Feature.ORDER_ITEMS);
        customerService.validateIfCustomerIDExists(customerID);
        log.debug("Request for ordering items");
        return orderService.orderItems(customerID, createOrderDTO);
    }

    @PostMapping(value = "customers/{customerID}/orders/{orderID}/order", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO reOrderItemsByOrderID(@RequestHeader String authorization, @PathVariable String customerID, @PathVariable String orderID) {
        securityService.validateAuthorization(authorization, Feature.ORDER_ITEMS);
        log.debug("Requesting to reorder order with ID " + orderID);
        String username = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length()))).split(":")[0];
        return orderService.getOrderByID(customerID, orderID, username);
    }
}
