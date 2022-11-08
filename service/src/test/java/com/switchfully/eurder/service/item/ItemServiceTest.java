package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.ItemRepository;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemServiceTest {
    private final ItemRepository itemRepository = new ItemRepository();
    private final ItemService itemService = new ItemService(itemRepository);

    @Test
    void gettingItemByID_givenInvalidID() {
        assertThatThrownBy(() -> itemService.getItemByID("invalidID"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Item with ID invalidID does not exist");
    }
}