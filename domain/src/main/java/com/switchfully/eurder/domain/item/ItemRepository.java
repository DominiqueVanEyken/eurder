package com.switchfully.eurder.domain.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class ItemRepository {
    private final Map<String, Item> itemRepository;
    private final Logger log = LoggerFactory.getLogger(ItemRepository.class);

    public ItemRepository() {
        itemRepository = new HashMap<>();
    }

    public Collection<Item> getAllItemsFromRepository() {
        return itemRepository.values();
    }

    public void addItem(Item item) {
        itemRepository.put(item.getItemID(), item);
        log.info("Created " + item);
    }

    public Item getItemByID(String itemID) {
        if (!itemRepository.containsKey(itemID)) {
            throw new NoSuchElementException("Item with ID ".concat(itemID).concat(" does not exist"));
        }
        return itemRepository.get(itemID);
    }

    public Price getItemPriceByItemID(String itemID) {
        return getItemByID(itemID).getPrice();
    }

    public boolean isItemInStock(String itemID) {
        return getItemByID(itemID).getStockCount() > 0;
    }
}
