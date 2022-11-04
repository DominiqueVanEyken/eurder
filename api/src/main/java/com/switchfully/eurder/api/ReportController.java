package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Feature;
import com.switchfully.eurder.service.customer.CustomerService;
import com.switchfully.eurder.service.report.ReportService;
import com.switchfully.eurder.service.report.dto.ShippingReportDTO;
import com.switchfully.eurder.service.report.dto.ReportDTO;
import com.switchfully.eurder.service.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ReportController {
    private final Logger log = LoggerFactory.getLogger(ReportController.class);
    private final SecurityService securityService;
    private final CustomerService customerService;
    private final ReportService reportService;

    public ReportController(SecurityService securityService, CustomerService customerService, ReportService reportService) {
        this.securityService = securityService;
        this.customerService = customerService;
        this.reportService = reportService;
    }

    @GetMapping(value = "customers/{customerID}/orders/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportDTO getReport(@RequestHeader String authorization, @PathVariable String customerID) {
        securityService.validateAuthorization(authorization, Feature.VIEW_REPORT);
        customerService.validateIfCustomerIDExists(customerID);
        log.debug("Requesting report for customer with ID " + customerID);
        return reportService.getReportForCustomer(customerID);
    }

    @GetMapping(value = "orders/shipping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShippingReportDTO getItemGroupsShippingToday(@RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Feature.GET_SHIPPING_ORDER);
        log.debug("Requesting shipping reports for today");
        return reportService.getShippingReportForToday();
    }
}
