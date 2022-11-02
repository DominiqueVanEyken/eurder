package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import com.switchfully.eurder.service.order.dto.ShippingReportDTO;
import com.switchfully.eurder.service.order.dto.report.ReportDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        orderMapper = new OrderMapper();
    }

    public OrderDTO orderItems(String customerID, CreateOrderDTO createOrderDTO) {
        Order order = orderMapper.mapDTOToOrder(customerID, createOrderDTO);
        orderRepository.createOrder(order);
        order = orderRepository.findOrderByID(order.getOrderID());
        return orderMapper.mapOrderToDTO(order);
    }

    public ReportDTO getReportForCustomer(String customerID) {
        List<Order> orders = orderRepository.getOrdersByCustomerID(customerID);
        return orderMapper.mapOrdersToReportDTO(orders);
    }

    public ShippingReportDTO getShippingReportForToday() {
        return orderMapper.mapShippingReportToShippingReportDTO(orderRepository.getShippingReportPerItemGroup());
    }

    public OrderDTO getOrderByID(String customerID, String orderID, String username) {
        return orderMapper.mapOrderToDTO(orderRepository.reorderOrderByID(customerID, orderID, username));
    }
}
