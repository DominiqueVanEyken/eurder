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
            assertThat(item.getPrice()).isEqualTo(price);
            assertThat(item.getStockCount()).isEqualTo(stockCount);
            assertThat(item.getStockStatus()).isEqualTo(StockStatus.STOCK_HIGH.toString());
            assertThat(item.toString()).isEqualTo(String.format("Item{itemID=%s, name=%s, description=%s, price=%s, stockCount=%d", item.getItemID(), name, description, price, stockCount));
        }

        @Test
        void creatingAnItemWithItemBuilder() {
            Item item = new ItemBuilder().setName(name).setDescription(description).setPrice(price).setStockCount(stockCount).build();

            assertThat(item).isNotNull();
            assertThat(item.getName()).isEqualTo(name);
            assertThat(item.getDescription()).isEqualTo(description);
            assertThat(item.getPrice()).isEqualTo(price);
            assertThat(item.getStockCount()).isEqualTo(stockCount);
            assertThat(item.getStockStatus()).isEqualTo(StockStatus.STOCK_HIGH.toString());
            assertThat(item.toString()).isEqualTo(String.format("Item{itemID=%s, name=%s, description=%s, price=%s, stockCount=%d", item.getItemID(), name, description, price, stockCount));
        }

        @Test
        void reduceStock() {
            int amountToReduce = 1;
            Item item = new Item(name, description, price, stockCount);
            item.reduceStockByAmount(amountToReduce);
            assertThat(item.getStockCount() == stockCount - amountToReduce).isTrue();
        }

        @Test
        void updateItem() {
            Item item = new ItemBuilder().setName(name).setDescription(description).setPrice(price).setStockCount(stockCount).build();
            String updateName = "update";

            item.updateItem(updateName, description, price.getPrice(), stockCount);
            assertThat(item.getName()).isEqualTo(updateName);
            assertThat(item.getDescription()).isEqualTo(description);
            assertThat(item.getPrice()).isEqualTo(price);
            assertThat(item.getStockCount()).isEqualTo(stockCount);
            assertThat(item.getStockStatus()).isEqualTo(StockStatus.STOCK_HIGH.toString());
            assertThat(item.toString()).isEqualTo(String.format("Item{itemID=%s, name=%s, description=%s, price=%s, stockCount=%d", item.getItemID(), updateName, description, price, stockCount));
        }
    }

    @Nested
    class givenInvalidData {
        @Test
        void itemNameIsNull() {
            assertThatThrownBy(() -> new Item(null, description, price, stockCount)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("The provided item name is not valid");
        }

        @Test
        void itemNameIsLessThanTwoCharacters() {
            assertThatThrownBy(() -> new Item("a", description, price, stockCount)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("The provided item name is not valid");
        }

        @Test
        void itemNameContainsOnlySpaces() {
            assertThatThrownBy(() -> new Item("  ", description, price, stockCount)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("The provided item name is not valid");
        }

        @Test
        void priceIsZero() {
            assertThatThrownBy(() -> new Item(name, description, new Price(0.0), stockCount)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("The provided price cannot be 0");
        }

        @Test
        void priceIsNegative() {
            assertThatThrownBy(() -> new Item(name, description, new Price(-1.0), stockCount)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("The provided price cannot be 0");
        }
    }

}