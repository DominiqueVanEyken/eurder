package com.switchfully.eurder.domain.item;

import com.switchfully.eurder.domain.Price.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemRepository {
    private final Map<String, Item> itemRepository;
    private final Logger log = LoggerFactory.getLogger(ItemRepository.class);

    public ItemRepository() {
        itemRepository = new HashMap<>();
        fillRepository();
    }

    private void fillRepository() {
        Item item1 = new ItemBuilder()
                .setName("name1")
                .setDescription("description")
                .setPrice(new Price(10.2))
                .setStockCount(1)
                .build();
        Item item2 = new ItemBuilder()
                .setName("name2")
                .setDescription("description")
                .setPrice(new Price(5.5))
                .setStockCount(5)
                .build();
        Item item3 = new ItemBuilder()
                .setName("name3")
                .setDescription("description")
                .setPrice(new Price(1.1))
                .setStockCount(15)
                .build();
        itemRepository.put(item1.getItemID(), item1);
        itemRepository.put(item2.getItemID(), item2);
        itemRepository.put(item3.getItemID(), item3);
    }

    public Collection<Item> getAllItemsFromRepository() {
        return itemRepository.values().stream().sorted().toList();
    }

    public void addItem(Item item) {
        itemRepository.put(item.getItemID(), item);
        log.info("Created " + item);
    }

    public Optional<Item> getItemByID(String itemID) {
        return itemRepository.values().stream()
                .filter(item -> item.getItemID().equals(itemID))
                .findFirst();
    }

    //TODO: Possible to move to Item or ItemService?
    public void reduceStockForItemByAmount(String itemID, int amount) {
        itemRepository.get(itemID).reduceStockByAmount(amount);
    }

    public Collection<Item> getAllItemsByStockStatusFilter(String stockStatus) {
        return itemRepository.values().stream()
                .filter(item -> item.getStockStatus().equals(stockStatus.toUpperCase()))
                .toList();
    }
}
