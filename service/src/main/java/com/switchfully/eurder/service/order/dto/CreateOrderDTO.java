package com.switchfully.eurder.service.order.dto;

import com.switchfully.eurder.service.itemgroup.dto.CreateItemGroupDTO;

import java.util.List;

public class CreateOrderDTO {
     private List<CreateItemGroupDTO> orderList;

     public CreateOrderDTO setOrderList(List<CreateItemGroupDTO> orderList) {
          this.orderList = orderList;
          return this;
     }

     public List<CreateItemGroupDTO> getOrderList() {
          return orderList;
     }

}
