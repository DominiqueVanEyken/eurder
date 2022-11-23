package com.switchfully.eurder.service.report;

import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.itemgroup.ItemGroupShippingReport;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.service.report.dto.ReportDTO;
import com.switchfully.eurder.service.report.dto.ShippingReportDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    private final OrderRepository orderRepository;
    private final ReportMapper reportMapper;
    private final CustomerRepository customerRepository;

    public ReportService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.reportMapper = new ReportMapper();
    }

    public ReportDTO getReportForCustomer(String customerID) {
        List<Order> orders = orderRepository.findOrdersByCustomerID(customerID);
        return reportMapper.mapOrdersToReportDTO(orders);
    }

    public ShippingReportDTO getShippingReportForToday() {
        List<Order> orders = orderRepository.findAll();
        List<ItemGroupShippingReport> itemGroupShippingReports = new ArrayList<>();
        for (Order order : orders) {
//            for (ItemGroup itemGroup : order.getOrderList()) {
//                if (doesItemGroupShipsToday(itemGroup)) {
//                    customerRepository.findById(order.getCustomerID())
//                            .ifPresent(customer -> itemGroupShippingReports.add(
//                                            new ItemGroupShippingReport(customer.getEmailAddress(), itemGroup)
//                                    )
//                            );
//                }
//            }
        }
        return reportMapper.mapShippingReportToShippingReportDTO(itemGroupShippingReports);
    }

    private boolean doesItemGroupShipsToday(ItemGroup itemGroup) {
        LocalDate shippingDate = itemGroup.getShippingDate();
        LocalDate today = LocalDate.now();
        return shippingDate.getYear() == today.getYear() && shippingDate.getMonth().equals(today.getMonth()) && shippingDate.getDayOfMonth() == today.getDayOfMonth();
    }
}
