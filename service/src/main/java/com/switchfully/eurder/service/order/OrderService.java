package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        orderMapper = new OrderMapper();
    }

    public OrderDTO createOrder(String customerID, CreateOrderDTO createOrderDTO) {
        Order order = orderMapper.mapDTOToOrder(customerID, createOrderDTO);
        orderRepository.createOrder(order);
        order = orderRepository.findOrderByID(order.getOrderID());
        return orderMapper.mapOrderToDTO(order);
    }

    public OrderDTO getOrderByID(String customerID, String orderID, String username) {
        return orderMapper.mapOrderToDTO(orderRepository.reorderOrderByID(customerID, orderID, username));
    }
}
