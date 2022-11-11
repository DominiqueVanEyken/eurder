package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.Price.Price;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ItemGroup {
    private static final int MINIMUM_ORDER_AMOUNT_REQUIREMENT = 1;
    private final String itemID;
    private final String itemName;
    private final int amount;
    private LocalDate shippingDate;
    private final Price pricePerUnit;
    private final Price totalPrice;

    public ItemGroup(String itemID, String itemName, int amount, LocalDate shippingDate, Price pricePerUnit) {
        this.itemID = validateItemID(itemID);
        this.itemName = itemName;
        this.amount = validateAmount(amount);
        this.shippingDate = shippingDate;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = new Price(pricePerUnit.getPrice() * amount);
    }

    private int validateAmount(int amount) {
        if (amount < MINIMUM_ORDER_AMOUNT_REQUIREMENT) {
            throw new IllegalArgumentException("The minimum requirement to order is " + MINIMUM_ORDER_AMOUNT_REQUIREMENT);
        }
        return amount;
    }

    public String validateItemID(String itemID) {
        if (itemID == null) {
            throw new IllegalArgumentException("The provided itemID is not valid");
        }
        boolean isValidItemID = Pattern.matches("IID[0-9]{8}", itemID);
        if (!isValidItemID) {
            throw new IllegalArgumentException("The provided itemID is not valid");
        }
        return itemID;
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getItemID() {
        return itemID;
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

    public String getPricePerUnit() {
        return pricePerUnit.toString();
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public double getTotalPriceAsDouble() {
        return totalPrice.getPrice();
    }

    @Override
    public String toString() {
        return String.format("ItemGroup{itemID=%s, amount=%d", itemID, amount);
    }
}
