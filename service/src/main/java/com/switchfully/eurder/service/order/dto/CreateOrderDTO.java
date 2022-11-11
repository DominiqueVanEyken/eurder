package com.switchfully.eurder.service.order.dto;

import java.util.List;

public class CreateOrderDTO {
     private String customerID;
     private List<CreateItemGroupDTO> orderList;

     public CreateOrderDTO setOrderList(List<CreateItemGroupDTO> orderList) {
          this.orderList = orderList;
          return this;
     }

     public List<CreateItemGroupDTO> getOrderList() {
          return orderList;
     }

     public String getCustomerID() {
          return customerID;
     }
}
