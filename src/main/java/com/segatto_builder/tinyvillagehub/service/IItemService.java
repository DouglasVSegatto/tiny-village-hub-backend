package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.dto.item.ItemListingDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.model.Item;

import java.util.List;
import java.util.UUID;

public interface IItemService {
    List<Item> findAllAvailableItems();
    List<ItemListingDto> findAllByOwnerId();
    List<Item> findActiveByOwnerId();
    Item findItemById(UUID itemId);
    ItemListingDto updateItem(UUID ownerId, ItemRequestDto itemDto);
    void deleteItem(UUID ownerId);
}
