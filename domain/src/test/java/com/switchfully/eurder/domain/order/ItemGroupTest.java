package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.Price.Price;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemGroupTest {

    private final Item itemHighStock = new Item("name", null, new Price(1.2), 100);
    private final Item itemLowStock = new Item("name", null, new Price(1.2), 1);
    private final String itemIDHighStock = itemHighStock.getItemID();
    private final String itemIDLowStock = itemLowStock.getItemID();
    private final int amount = 10;

    @Nested
    class givenValidData {
        @Test
        void creatingAnItemGroup_itemsInStock() {
            ItemGroup itemGroup = new ItemGroup(itemIDHighStock, amount);
            itemGroup.setShippingDateAndPrice(itemHighStock);

            assertThat(itemGroup).isNotNull();
            assertThat(itemGroup.getItemID()).isEqualTo(itemIDHighStock);
            assertThat(itemGroup.getAmount()).isEqualTo(amount);
            assertThat(itemGroup.getPricePerUnit()).isEqualTo(itemHighStock.getPrice().toString());
            assertThat(itemGroup.getShippingDate()).isEqualTo(LocalDate.now().plusDays(1));
            assertThat(itemGroup.getTotalPrice()).isEqualTo(new Price(itemHighStock.getPrice().getPrice() * amount));
            assertThat(itemGroup.getTotalPriceAsDouble()).isEqualTo(itemHighStock.getPrice().getPrice() * amount);
            assertThat(itemGroup.toString()).isEqualTo(String.format("ItemGroup{itemID=%s, amount=%d", itemIDHighStock, amount));
        }

        @Test
        void creatingAnItemGroup_itemsNotInStock() {
            ItemGroup itemGroup = new ItemGroup(itemIDLowStock, amount);
            itemGroup.setShippingDateAndPrice(itemLowStock);

            assertThat(itemGroup).isNotNull();
            assertThat(itemGroup.getItemID()).isEqualTo(itemIDLowStock);
            assertThat(itemGroup.getAmount()).isEqualTo(amount);
            assertThat(itemGroup.getPricePerUnit()).isEqualTo(itemHighStock.getPrice().toString());
            assertThat(itemGroup.getShippingDate()).isEqualTo(LocalDate.now().plusDays(7));
            assertThat(itemGroup.getTotalPrice()).isEqualTo(new Price(itemHighStock.getPrice().getPrice() * amount));
            assertThat(itemGroup.getTotalPriceAsDouble()).isEqualTo(itemHighStock.getPrice().getPrice() * amount);
            assertThat(itemGroup.toString()).isEqualTo(String.format("ItemGroup{itemID=%s, amount=%d", itemIDLowStock, amount));
        }
    }

    @Nested
    class givenInvalidData {
        @Test
        void withIDIsNull() {
            String errorMessage = "The provided itemID is not valid";
            assertThatThrownBy(() -> new ItemGroup(null, amount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
        }

        @Test
        void withIDOfIncorrectFormat() {
            String errorMessage = "The provided itemID is not valid";
            assertThatThrownBy(() -> new ItemGroup("invalidID", amount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
        }

        @Test
        void withAmountLessThanOne() {
            String errorMessage = "The minimum requirement to order is 1";
            assertThatThrownBy(() -> new ItemGroup(itemIDLowStock, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(errorMessage);
        }
    }
}