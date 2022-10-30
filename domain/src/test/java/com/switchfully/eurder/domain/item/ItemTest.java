package com.switchfully.eurder.domain.item;

import com.switchfully.eurder.domain.Price.Price;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemTest {

    private final String name = "itemName";
    private final String description = "description of the item";
    private final Price price = new Price(1.1);
    private final int stockCount = 12;

    @Nested
    class givenValidData {
        @Test
        void creatingAnItem() {
            Item item = new Item(name, description, price, stockCount);

            assertThat(item).isNotNull();
            assertThat(item.getName()).isEqualTo(name);
            assertThat(item.getDescription()).isEqualTo(description);
            assertThat(item.getPriceWithUnit()).isEqualTo(price.toString());
            assertThat(item.getStockCount()).isEqualTo(stockCount);
            assertThat(item.toString()).isEqualTo(String.format("Item{name=%s, description=%s, price=%s, stockCount=%d", name, description, price, stockCount));
        }
        @Test
        void creatingAnItemWithItemBuilder() {
            Item item = new ItemBuilder()
                    .setName(name)
                    .setDescription(description)
                    .setPrice(price)
                    .setStockCount(stockCount)
                    .build();

            assertThat(item).isNotNull();
            assertThat(item.getName()).isEqualTo(name);
            assertThat(item.getDescription()).isEqualTo(description);
            assertThat(item.getPriceWithUnit()).isEqualTo(price.toString());
            assertThat(item.getStockCount()).isEqualTo(stockCount);
            assertThat(item.toString()).isEqualTo(String.format("Item{name=%s, description=%s, price=%s, stockCount=%d", name, description, price, stockCount));
        }

        @Test
        void reduceStock() {
            int amountToReduce = 1;
            Item item = new Item(name, description, price, stockCount);
            item.reduceStockByAmount(amountToReduce);
            assertThat(item.getStockCount() == stockCount - amountToReduce).isTrue();
        }
    }

    @Nested
    class givenInvalidData {
        @Test
        void itemNameIsNull() {
            assertThatThrownBy(() -> new Item(null, description, price, stockCount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("The provided item name is not valid");
        }
        @Test
        void itemNameIsLessThanTwoCharacters() {
            assertThatThrownBy(() -> new Item("a", description, price, stockCount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("The provided item name is not valid");
        }
        @Test
        void itemNameContainsOnlySpaces() {
            assertThatThrownBy(() -> new Item("  ", description, price, stockCount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("The provided item name is not valid");
        }
        @Test
        void priceIsZero() {
            assertThatThrownBy(() -> new Item(name, description, new Price(0.0), stockCount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("The provided price cannot be 0");
        }
        @Test
        void priceIsNegative() {
            assertThatThrownBy(() -> new Item(name, description, new Price(-1.0), stockCount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("The provided price cannot be 0");
        }
    }

}