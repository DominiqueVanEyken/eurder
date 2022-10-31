package com.switchfully.eurder.domain.item;

import com.switchfully.eurder.domain.Price.Price;
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

    public Item getItemByID(String itemID) {
        if (!itemRepository.containsKey(itemID)) {
            throw new NoSuchElementException("Item with ID ".concat(itemID).concat(" does not exist"));
        }
        return itemRepository.get(itemID);
    }

    public void reduceStockForItemByAmount(String itemID, int amount) {
        itemRepository.get(itemID).reduceStockByAmount(amount);
    }
}
