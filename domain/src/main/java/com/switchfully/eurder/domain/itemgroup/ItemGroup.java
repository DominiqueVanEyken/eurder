package com.switchfully.eurder.domain.itemgroup;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.order.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "ITEM_GROUP")
public class ItemGroup implements Serializable {
    private static final int MINIMUM_ORDER_AMOUNT_REQUIREMENT = 1;
    @Id
    @GeneratedValue(generator = "ITEM_GROUP_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ITEM_GROUP_SEQ", sequenceName = "ITEM_GROUP_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "ITEM_GROUP_ID")
    private Long itemGroupID;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ORDER_ID")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;
    @Column(name = "ITEM_NAME")
    private String itemName;
    @Column(name = "AMOUNT")
    private int amount;
    @Column(name = "SHIPPING_DATE")
    private LocalDate shippingDate;
    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "PRICE_PER_UNIT"))
    private Price pricePerUnit;
    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "TOTAL_PRICE"))
    private Price totalPrice;

    //TODO: take out itemName, shippingDate and Price -> already defined in Item so to be called upon in constructor
    public ItemGroup(Order order, Item item, String itemName, int amount, LocalDate shippingDate, Price pricePerUnit) {
        this.order = order;
        this.item = item;
        this.itemName = itemName;
        this.amount = validateAmount(amount);
        this.shippingDate = shippingDate;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = new Price(pricePerUnit.getPrice() * amount);
    }

    public ItemGroup() {
    }

    private int validateAmount(int amount) {
        if (amount < MINIMUM_ORDER_AMOUNT_REQUIREMENT) {
            throw new IllegalArgumentException("The minimum requirement to order is " + MINIMUM_ORDER_AMOUNT_REQUIREMENT);
        }
        return amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Item getItem() {
        return item;
    }

    public long getItemID() {
        return item.getItemID();
    }

    public String getItemName() {
        return itemName;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public Price getPricePerUnit() {
        return pricePerUnit;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public double getTotalPriceAsDouble() {
        return totalPrice.getPrice();
    }

    @Override
    public String toString() {
        return String.format("ItemGroup{itemID=%s, amount=%d}", getItemID(), amount);
    }
}
