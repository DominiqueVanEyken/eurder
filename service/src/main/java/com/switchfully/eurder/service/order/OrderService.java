package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.item.ItemRepository;
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

    public OrderDTO orderItems(String customerID, CreateOrderDTO createOrderDTO) {
        Order order = orderMapper.mapDTOToOrder(customerID, createOrderDTO);
        System.out.println("orderService: " + order);
        order = orderRepository.createOrder(order);
        return orderMapper.mapOrderToDTO(order);
    }
}
