package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Feature;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.OrderService;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import com.switchfully.eurder.service.security.SecurityService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    private final SecurityService securityService;
    private final OrderService orderService;

    public OrderController(SecurityService securityService, OrderService orderService) {
        this.securityService = securityService;
        this.orderService = orderService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTO orderItems(@RequestHeader String authorization, @RequestBody CreateOrderDTO createOrderDTO) {
        securityService.validateAuthorization(authorization, Feature.ORDER_ITEMS);
        //todo
        return null;
    }

}
