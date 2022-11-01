package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Feature;
import com.switchfully.eurder.service.order.OrderService;
import com.switchfully.eurder.service.order.dto.ShippingReportDTO;
import com.switchfully.eurder.service.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final SecurityService securityService;

    public OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @GetMapping(value = "shipping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShippingReportDTO getItemGroupsShippingToday(@RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Feature.GET_SHIPPING_ORDER);
        log.debug("Requesting shipping reports for today");
        return orderService.getShippingReportForToday();
    }
}
