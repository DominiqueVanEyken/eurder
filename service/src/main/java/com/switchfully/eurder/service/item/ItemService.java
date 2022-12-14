package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.StockStatus;
import com.switchfully.eurder.service.item.dto.CreateItemDTO;
import com.switchfully.eurder.service.item.dto.ItemDTO;
import com.switchfully.eurder.service.item.dto.UpdateItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ItemService {
    private final Logger log = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        itemMapper = new ItemMapper();
    }

    public ItemDTO createItem(CreateItemDTO createItemDTO) {
        Item item = itemMapper.mapDTOToItem(createItemDTO);
        itemRepository.save(item);
        log.info("Saving item with ID " + item.getItemID());
        return itemMapper.mapItemToDTO(item);
    }

    public List<ItemDTO> requestAllItems() {
        log.debug("Getting all items");
        return itemMapper.mapItemToDTO(itemRepository.findAll());
    }

    public List<ItemDTO> requestItemsOnStockStatusFiler(StockStatus stockStatus) {
        if (!Arrays.asList(StockStatus.values()).contains(stockStatus)) {
            throw new NoSuchElementException("Provided stock status does not exist. Choose from " + Arrays.toString(StockStatus.values()));
        }
        log.info("Getting all items with stock status " + stockStatus);
        return itemMapper.mapItemToDTO(itemRepository.findItemByStockStatus(stockStatus));
    }

    public Item getItemByID(long itemID) {
        return itemRepository.findById(itemID).orElseThrow(() -> new NoSuchElementException("Item with ID " + itemID + " does not exist"));
    }

    public ItemDTO updateItemByID(long itemID, UpdateItemDTO updateItemDTO) {
        Item itemToUpdate = getItemByID(itemID);
        itemToUpdate.updateItem(updateItemDTO.getName(), updateItemDTO.getDescription(), updateItemDTO.getPrice(), updateItemDTO.getStockCount());
        return itemMapper.mapItemToDTO(itemToUpdate);
    }
}
