package com.switchfully.eurder.domain.item;

import com.switchfully.eurder.domain.Price.Price;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemRepositoryTest {
    private final ItemRepository itemRepository = new ItemRepository();

    @Test
    void addingItemToRepository() {
        int before = itemRepository.getAllItemsFromRepository().size();
        itemRepository.addItem(new ItemBuilder()
                .setName("name")
                .setDescription("description")
                .setPrice(new Price(1.1))
                .setStockCount(12)
                .build()
        );
        int after = itemRepository.getAllItemsFromRepository().size();
        assertThat(before).isEqualTo(after - 1);
    }

    @Test
    void gettingItemByID_givenValidID() {
        Item item = new ItemBuilder()
                .setName("name")
                .setDescription("description")
                .setPrice(new Price(1.1))
                .setStockCount(12)
                .build();
        itemRepository.addItem(item);
        Item result = itemRepository.getItemByID(item.getItemID());
        assertThat(result).isEqualTo(item);
    }

    @Test
    void gettingItemByID_givenInvalidID() {
        assertThatThrownBy(() -> itemRepository.getItemByID("invalidID"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Item with ID invalidID does not exist");
    }

    @Test
    void reducingTheStockOfAnItem() {
        int amountToReduce = 2;
        Item item = new ItemBuilder()
                .setName("name")
                .setDescription("description")
                .setPrice(new Price(1.1))
                .setStockCount(12)
                .build();
        int stockBefore = item.getStockCount();
        itemRepository.addItem(item);
        itemRepository.reduceStockForItemByAmount(item.getItemID(), amountToReduce);
        assertThat(item.getStockCount() == stockBefore - amountToReduce).isTrue();
    }

    @Test
    void getAllItemsByStockStatusFilter() {
        Collection<Item> itemList = itemRepository.getAllItemsByStockStatusFilter("low");

        assertThat(itemList).isNotNull();
        assertThat(itemList.size()).isEqualTo(1);
    }
}