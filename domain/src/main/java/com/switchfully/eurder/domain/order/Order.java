package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "\"ORDER\"")
public class Order {
    private static final String ORDER_ID_PREFIX = "OID";
    private static int order_id_suffix = 1001;
    @Id
    @Column(name = "ORDER_ID")
    private String orderID;
    @Column(name = "CUSTOMER_ID")
    private String customerID;
    @Column(name = "ORDER_DATE")
    private LocalDate orderDate;
    @OneToMany
    @Column(name = "ITEM_GROUP_ID")
    @Transient
    private List<ItemGroup> orderList;
    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "TOTAL_PRICE"))
    private Price totalPrice;

    public Order(String customerID) {
        this.customerID = customerID;
//        this.orderList = orderList;
        this.orderDate = LocalDate.now();
//        totalPrice = new Price(orderList.stream()
//                .mapToDouble(ItemGroup::getTotalPriceAsDouble)
//                .sum());
        totalPrice = new Price(0.0);
        orderID = ORDER_ID_PREFIX + orderDate.getYear() + order_id_suffix++;
    }

    public Order() {
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
    public void updatePrice(List<ItemGroup> itemGroups) {
        this.orderList = itemGroups;
        totalPrice = new Price(orderList.stream()
                .mapToDouble(ItemGroup::getTotalPriceAsDouble)
                .sum());
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return String.format("Order{orderID=%s, customerID=%s, orderDate=%s}", orderID, customerID, orderDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderID, order.orderID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID);
    }
}
