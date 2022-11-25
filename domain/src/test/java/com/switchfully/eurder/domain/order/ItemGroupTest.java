package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.itemgroup.ItemGroupBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemGroupTest {
    private final Item itemHighStock = new Item("name", null, new Price(1.2), 100);
    private final Item itemLowStock = new Item("name", null, new Price(1.2), 1);
    private final long itemIDHighStock = itemHighStock.getItemID();
    private final long itemIDLowStock = itemLowStock.getItemID();
    private final Order order = new Order(UUID.randomUUID().toString());
    private final int amount = 10;

    @Nested
    class ItemGroupBuilderTest {
        @Test
        void creatingAnItemGroup_usingItemGroupBuilder() {
            String name = "name";
            LocalDate shippingDate = LocalDate.now();
            Price pricePerUnit = new Price(1.2);

            ItemGroup builder = new ItemGroupBuilder()
                    .setItem(itemHighStock)
                    .setItemName(name)
                    .setAmount(amount)
                    .setShippingDate(shippingDate)
                    .setPricePerUnit(pricePerUnit)
                    .build();

            assertThat(builder.getItemID()).isEqualTo(itemHighStock.getItemID());
            assertThat(builder.getItemName()).isEqualTo(name);
            assertThat(builder.getAmount()).isEqualTo(amount);
            assertThat(builder.getShippingDate()).isEqualTo(shippingDate.plusDays(1));
            assertThat(builder.getPricePerUnit()).isEqualTo(pricePerUnit);
            assertThat(builder.getTotalPrice()).isEqualTo(new Price(pricePerUnit.getPrice() * amount));
        }
    }

    @Nested
    class givenValidData {
        @Test
        void creatingAnItemGroup_itemsInStock() {
            ItemGroup itemGroup = new ItemGroup(order, itemHighStock, amount);

            assertThat(itemGroup).isNotNull();
            assertThat(itemGroup.getItemID()).isEqualTo(itemIDHighStock);
            assertThat(itemGroup.getItemName()).isEqualTo(itemHighStock.getName());
            assertThat(itemGroup.getAmount()).isEqualTo(amount);
            assertThat(itemGroup.getPricePerUnit()).isEqualTo(itemHighStock.getPrice());
            assertThat(itemGroup.getShippingDate()).isEqualTo(LocalDate.now().plusDays(1));
            assertThat(itemGroup.getTotalPrice()).isEqualTo(new Price(itemHighStock.getPrice().getPrice() * amount));
            assertThat(itemGroup.getTotalPriceAsDouble()).isEqualTo(itemHighStock.getPrice().getPrice() * amount);
            assertThat(itemGroup.toString()).isEqualTo(String.format("ItemGroup{itemID=%s, amount=%d}", itemIDHighStock, amount));
        }

        @Test
        void setShippingDateManually() {
            LocalDate date = LocalDate.of(2022, 11, 1);
            ItemGroup itemGroup = new ItemGroup(order, itemHighStock, amount);
            itemGroup.setShippingDate(date);

            assertThat(itemGroup.getShippingDate()).isEqualTo(date);
        }

        @Test
        void creatingAnItemGroup_itemsNotInStock() {
            ItemGroup itemGroup = new ItemGroup(order, itemLowStock, amount);

            assertThat(itemGroup).isNotNull();
            assertThat(itemGroup.getItemID()).isEqualTo(itemIDLowStock);
            assertThat(itemGroup.getItemName()).isEqualTo(itemLowStock.getName());
            assertThat(itemGroup.getAmount()).isEqualTo(amount);
            assertThat(itemGroup.getPricePerUnit()).isEqualTo(itemHighStock.getPrice());
            assertThat(itemGroup.getShippingDate()).isEqualTo(LocalDate.now().plusDays(7));
            assertThat(itemGroup.getTotalPrice()).isEqualTo(new Price(itemHighStock.getPrice().getPrice() * amount));
            assertThat(itemGroup.getTotalPriceAsDouble()).isEqualTo(itemHighStock.getPrice().getPrice() * amount);
            assertThat(itemGroup.toString()).isEqualTo(String.format("ItemGroup{itemID=%s, amount=%d}", itemIDLowStock, amount));
        }
    }

    @Nested
    class givenInvalidData {

        @Test
        void withAmountLessThanOne() {
            String errorMessage = "The minimum requirement to order is 1";
            assertThatThrownBy(() -> new ItemGroup(order, itemLowStock,0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
        }
    }
}