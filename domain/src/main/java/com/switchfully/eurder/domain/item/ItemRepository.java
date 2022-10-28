package com.switchfully.eurder.domain.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository {
    private final Map<String, Item> itemRepository;
    private final Logger log = LoggerFactory.getLogger(ItemRepository.class);

    public ItemRepository() {
        itemRepository = new HashMap<>();
    }
    public void addItem(Item item) {
        itemRepository.put(item.getName(), item);
        log.info("Created " + item);
    }
}
