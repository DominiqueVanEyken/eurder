package com.switchfully.eurder.api;

import com.switchfully.eurder.service.customer.CustomerService;
import com.switchfully.eurder.service.order.OrderService;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping
public class OrderController {
    private final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @PostMapping(value = "customers/{customerID}/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ORDER_ITEMS')")
    public OrderDTO orderItems(@RequestHeader String authorization, @PathVariable String customerID, @RequestBody CreateOrderDTO createOrderDTO) {
        //TODO: fix username with accessToken => check parkshark
        String username = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length()))).split(":")[0]; // todo: return vanuit securityService
        customerService.validateIfCustomerIDBelongsToUsername(customerID, username);//TODO deel maken van securityService
        log.debug("Request for ordering items");
        return orderService.createOrder(customerID, createOrderDTO);
    }

    @PostMapping(value = "customers/{customerID}/orders/{orderID}/reorder", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ORDER_ITEMS')")
    public OrderDTO reOrderItemsByOrderID(@PathVariable String customerID, @PathVariable long orderID) {
        log.debug("Requesting to reorder order with ID " + orderID);
        return orderService.reOrderByOrderID(customerID, orderID);
    }
}
