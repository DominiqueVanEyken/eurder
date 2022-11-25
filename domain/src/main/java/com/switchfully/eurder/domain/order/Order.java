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
    @Id
    @GeneratedValue(generator = "ORDER_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ORDER_SEQ", sequenceName = "ORDER_SEQ", initialValue = 101, allocationSize = 1)
    @Column(name = "ORDER_ID")
    private long orderID;
    @Column(name = "CUSTOMER_ID")
    private String customerID;
    @Column(name = "ORDER_DATE")
    private LocalDate orderDate;
    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "TOTAL_PRICE"))
    private Price totalPrice;

    public Order(String customerID) {
        this.customerID = customerID;
        this.orderDate = LocalDate.now();
        totalPrice = new Price(0.0);
    }

    public Order() {
    }

    public long getOrderID() {
        return orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void updatePrice(List<ItemGroup> itemGroups) {
        totalPrice = new Price(itemGroups.stream()
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
