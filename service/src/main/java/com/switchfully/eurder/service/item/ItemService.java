package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.service.item.dto.CreateItemDTO;
import com.switchfully.eurder.service.item.dto.ItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        itemMapper = new ItemMapper();
    }

    public ItemDTO addNewItemToStock(CreateItemDTO createItemDTO) {
        Item item = itemMapper.mapDTOToItem(createItemDTO);
        itemRepository.addItem(item);
        return itemMapper.mapItemToDTO(item);
    }

    public List<ItemDTO> getAllItems() {
        return itemMapper.mapItemToDTO(itemRepository.getAllItemsFromRepository());
    }

    public List<ItemDTO> getItemsOnStockStatusFiler(String stockStatus) {
        return itemMapper.mapItemToDTO(itemRepository.getAllItemsByStockStatusFilter(stockStatus));
    }
}
