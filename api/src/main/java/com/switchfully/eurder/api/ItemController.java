package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Feature;
import com.switchfully.eurder.service.item.ItemService;
import com.switchfully.eurder.service.item.dto.CreateItemDTO;
import com.switchfully.eurder.service.item.dto.ItemDTO;
import com.switchfully.eurder.service.item.dto.UpdateItemDTO;
import com.switchfully.eurder.service.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
public class ItemController {

    private final SecurityService securityService;
    private final ItemService itemService;
    private final Logger log = LoggerFactory.getLogger(ItemController.class);

    public ItemController(SecurityService securityService, ItemService itemService) {
        this.securityService = securityService;
        this.itemService = itemService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDTO> getAllItems(@RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Feature.CHECK_STOCK);
        log.debug("Request for all items in stock");
        return itemService.requestAllItems();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "stockStatus")
    public List<ItemDTO> getAllItemsOnStockStatusFilter(@RequestHeader String authorization, @RequestParam String stockStatus) {
        securityService.validateAuthorization(authorization, Feature.CHECK_STOCK);
        log.debug("Request for all items in stock with stockStatus filter on " + stockStatus);
        return itemService.requestItemsOnStockStatusFiler(stockStatus);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO addNewItemToStock(@RequestHeader String authorization, @RequestBody CreateItemDTO createItemDTO) {
        securityService.validateAuthorization(authorization, Feature.CREATE_ITEM);
        log.debug("request to add new item to stock");
        return itemService.createItem(createItemDTO);
    }

    @PutMapping(value = "{itemID}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ItemDTO updateAnExistingItem(@RequestHeader String authorization,@PathVariable Long itemID, @RequestBody UpdateItemDTO updateItemDTO) {
        securityService.validateAuthorization(authorization, Feature.UPDATE_ITEM);
        log.debug("Request to update item with ID " + itemID);
        return itemService.updateItemByID(itemID, updateItemDTO);
    }

}
