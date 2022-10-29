package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.Price.Price;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private static final String ORDER_ID_PREFIX = "OID";
    private static int order_id_suffix = 1001;
    private final String orderID;
    private final String customerID;
    private final LocalDate orderDate;
    private final List<ItemGroup> orderList;
    private Price totalPrice;

    public Order(String customerID, List<ItemGroup> orderList) {
        this.customerID = customerID;
        this.orderList = orderList;
        this.orderDate = LocalDate.now();
        orderID = ORDER_ID_PREFIX + orderDate.getYear() + order_id_suffix++;
    }
    public static void calculateTotalPrice(Order order) {
        order.totalPrice = new Price(order.getOrderList().stream()
                .mapToDouble(ItemGroup::getTotalPriceAsDouble)
                .sum());
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public List<ItemGroup> getOrderList() {
        return orderList;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return String.format("Order{orderID=%s, customerID=%s, orderDate=%s}", orderID, customerID, orderDate);
    }
}
