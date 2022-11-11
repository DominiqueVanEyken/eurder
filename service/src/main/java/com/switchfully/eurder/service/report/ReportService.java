package com.switchfully.eurder.service.report;

import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.service.report.dto.ReportDTO;
import com.switchfully.eurder.service.report.dto.ShippingReportDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final OrderRepository orderRepository;
    private final ReportMapper reportMapper;

    public ReportService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.reportMapper = new ReportMapper();
    }

    public ReportDTO getReportForCustomer(String customerID) {
        List<Order> orders = orderRepository.getOrdersByCustomerID(customerID);
        return reportMapper.mapOrdersToReportDTO(orders);
    }

    public ShippingReportDTO getShippingReportForToday() {
        return reportMapper.mapShippingReportToShippingReportDTO(orderRepository.getItemGroupsShippingToday());
    }
}
