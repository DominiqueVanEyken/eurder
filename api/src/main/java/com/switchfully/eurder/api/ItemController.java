package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.item.StockStatus;
import com.switchfully.eurder.service.item.ItemService;
import com.switchfully.eurder.service.item.dto.CreateItemDTO;
import com.switchfully.eurder.service.item.dto.ItemDTO;
import com.switchfully.eurder.service.item.dto.UpdateItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
@CrossOrigin
public class ItemController {

    private final ItemService itemService;
    private final Logger log = LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDTO> getAllItems() {
        log.debug("Request for all items in stock");
        return itemService.requestAllItems();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "stockStatus")
    @PreAuthorize("hasAnyAuthority('CHECK_STOCK')")
    public List<ItemDTO> getAllItemsOnStockStatusFilter(@RequestParam StockStatus stockStatus) {
        log.debug("Request for all items in stock with stockStatus filter on " + stockStatus);
        return itemService.requestItemsOnStockStatusFiler(stockStatus);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('CREATE_ITEM')")
    public ItemDTO addNewItemToStock(@RequestBody CreateItemDTO createItemDTO) {
        log.debug("request to add new item to stock");
        return itemService.createItem(createItemDTO);
    }

    @PutMapping(value = "{itemID}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('UPDATE_ITEM')")
    public ItemDTO updateAnExistingItem(@PathVariable Long itemID, @RequestBody UpdateItemDTO updateItemDTO) {
        log.debug("Request to update item with ID " + itemID);
        return itemService.updateItemByID(itemID, updateItemDTO);
    }

}
