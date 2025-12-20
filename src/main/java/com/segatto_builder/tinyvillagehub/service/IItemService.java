package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.dto.item.ItemResponseDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.model.Item;

import java.util.List;
import java.util.UUID;

public interface IItemService {
    List<Item> findAllAvailable();
    List<ItemResponseDto> findAllByOwnerId();
    List<Item> findActiveByOwnerId();
    Item findById(UUID itemId);
    void update(UUID id, ItemRequestDto itemDto);
    void delete(UUID id);
    void create(ItemRequestDto dto);
    void activateItem(UUID id);
    void deactivateItem(UUID id);
    void completeItem(UUID id);
    void pendingItem(UUID id);
}
