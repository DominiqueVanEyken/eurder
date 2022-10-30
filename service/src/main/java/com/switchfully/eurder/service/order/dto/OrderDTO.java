package com.switchfully.eurder.service.order.dto;

import com.switchfully.eurder.service.order.dto.itemgroup.ItemGroupDTO;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private String orderID;
    private String customerID;
    private LocalDate orderDate;
    private List<ItemGroupDTO> orderList;
    private String totalPrice;

    public String getOrderID() {
        return orderID;
    }

    public OrderDTO setOrderID(String orderID) {
        this.orderID = orderID;
        return this;
    }

    public String getCustomerID() {
        return customerID;
    }

    public OrderDTO setCustomerID(String customerID) {
        this.customerID = customerID;
        return this;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public OrderDTO setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public List<ItemGroupDTO> getOrderList() {
        return orderList;
    }

    public OrderDTO setOrderList(List<ItemGroupDTO> orderList) {
        this.orderList = orderList;
        return this;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public OrderDTO setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
}
