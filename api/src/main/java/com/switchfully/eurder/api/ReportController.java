package com.switchfully.eurder.api;

import com.switchfully.eurder.service.customer.CustomerService;
import com.switchfully.eurder.service.report.ReportService;
import com.switchfully.eurder.service.report.dto.ShippingReportDTO;
import com.switchfully.eurder.service.report.dto.ReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping
public class ReportController {
    private final Logger log = LoggerFactory.getLogger(ReportController.class);
    private final CustomerService customerService;
    private final ReportService reportService;

    public ReportController(CustomerService customerService, ReportService reportService) {
        this.customerService = customerService;
        this.reportService = reportService;
    }

    @GetMapping(value = "customers/{customerID}/orders/report", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('VIEW_REPORT')")
    public ReportDTO getReport(@RequestHeader String authorization, @PathVariable String customerID) {
        //TODO: fix username with accessToken => check parkshark
        String username = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length()))).split(":")[0];
        customerService.validateIfCustomerIDBelongsToUsername(customerID, username); //TODO: naar securityService
        log.debug("Requesting report for customer with ID " + customerID);
        return reportService.getReportForCustomer(customerID);
    }

    @GetMapping(value = "orders/shipping", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('GET_SHIPPING_ORDER')")
    public ShippingReportDTO getItemGroupsShippingToday() {
        log.debug("Requesting shipping reports for today");
        return reportService.getShippingReportForToday();
    }
}
