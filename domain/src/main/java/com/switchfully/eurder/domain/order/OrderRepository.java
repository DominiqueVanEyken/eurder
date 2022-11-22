package com.switchfully.eurder.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
List<Order> findOrdersByCustomerID(String customer);

//    private void fillOrderRepository() {
//        List<Item> items = itemRepository.findAll().stream()
//                .limit(3)
//                .toList();
//        ItemGroup itemGroup1 = new ItemGroup(items.get(0).getItemID(), items.get(0).getName(), 1, items.get(0).getShippingDateForAmount(1), items.get(0).getPrice());
//        ItemGroup itemGroup2 = new ItemGroup(items.get(1).getItemID(), items.get(1).getName(), 2, items.get(1).getShippingDateForAmount(2), items.get(1).getPrice());
//        ItemGroup itemGroup3 = new ItemGroup(items.get(2).getItemID(), items.get(2).getName(), 3, items.get(1).getShippingDateForAmount(3), items.get(2).getPrice());
//        Order order1 = new Order("CID20221001", List.of(itemGroup1, itemGroup2, itemGroup3));
//        Order order2 = new Order("CID20221003", List.of(itemGroup1, itemGroup2, itemGroup3));
//        Order order3 = new Order("CID20221002", List.of(itemGroup1, itemGroup2, itemGroup3));
//        orderRepository.put(order1.getOrderID(), order1);
//        orderRepository.put(order2.getOrderID(), order2);
//        orderRepository.put(order3.getOrderID(), order3);
//    }


}
