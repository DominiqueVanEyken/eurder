package com.switchfully.eurder.domain.item;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}