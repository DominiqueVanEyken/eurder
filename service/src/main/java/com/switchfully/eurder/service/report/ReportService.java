package com.switchfully.eurder.service.report;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.itemgroup.ItemGroupRepository;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.service.report.dto.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ReportService {
    private final OrderRepository orderRepository;
    private final ReportMapper reportMapper;
    private final CustomerRepository customerRepository;
    private final ItemGroupRepository itemGroupRepository;

    public ReportService(OrderRepository orderRepository, CustomerRepository customerRepository, ItemGroupRepository itemGroupRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.itemGroupRepository = itemGroupRepository;
        this.reportMapper = new ReportMapper();
    }

    public ReportDTO getReportForCustomer(String customerID) {
        List<Order> orders = orderRepository.findOrdersByCustomerID(customerID);
        List<OrderReportDTO> orderReportDTOS = new ArrayList<>();
        double totalPrice = 0;
        for (Order order : orders) {
            List<ItemGroupReportDTO> itemGroupReportDTOS = reportMapper.mapItemGroupToItemGroupReportDTO(itemGroupRepository.findByOrder(order));
            orderReportDTOS.add(reportMapper.mapOrderToOrderReportDTO(order, itemGroupReportDTOS));
            totalPrice += order.getTotalPrice().getPrice();
        }
        return new ReportDTO()
                .setOrderReports(orderReportDTOS)
                .setTotalPrice(new Price(totalPrice).toString());
    }

    public ShippingReportDTO getShippingReportForToday() {
        List<ItemGroup> itemGroups = itemGroupRepository.findAllByShippingDateEquals(LocalDate.now());
        List<ItemGroupShippingReportDTO> itemGroupShippingReportDTOS = new ArrayList<>();
        for (ItemGroup itemGroup : itemGroups) {
            Customer customer = customerRepository.findById(itemGroup.getOrder().getCustomerID()).orElseThrow(() -> new NoSuchElementException("Customer not found"));
            itemGroupShippingReportDTOS.add(reportMapper.mapItemGroupToItemGroupShippingReportDTO(customer.getAddress(), itemGroup));
        }
        return reportMapper.mapShippingReportToShippingReportDTO(itemGroupShippingReportDTOS);
    }
}
