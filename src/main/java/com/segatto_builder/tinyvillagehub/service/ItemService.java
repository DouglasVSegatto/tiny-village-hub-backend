package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.dto.item.ItemListingDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.model.Item;
import com.segatto_builder.tinyvillagehub.model.enums.ItemStatus;
import com.segatto_builder.tinyvillagehub.repository.ItemRepository;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {

    private final ItemRepository itemRepository;
    private final IAuthFacade authFacade;

    //USER RELATED - TODO improve as it goes.
    @Override
    public List<ItemListingDto> findAllByOwnerId() {
        UUID ownerId = authFacade.getCurrentUserId();
        List<Item> items = findOwnerItems(ownerId);
        return items.stream()
                .map(ItemListingDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> findActiveByOwnerId() {
        UUID owner_id = authFacade.getCurrentUserId();
        return findOwnerItemsByStatus(owner_id, ItemStatus.AVAILABLE);
    }

    @Override
    public List<Item> findAllAvailableItems() {
        return itemRepository.findByStatus(ItemStatus.AVAILABLE);
    }

    @Override
    public Item findItemById(UUID itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with ID: " + itemId));
    }

    @Override
    public void deleteItem(UUID itemId) {
        Item item = findItemById(itemId);
        UUID userId = authFacade.getCurrentUserId();
        // Authorization Check
        if (!item.getOwner().getId().equals(userId)) {
            throw new SecurityException("User is not authorized to delete this item.");
        }
        itemRepository.delete(item);
    }

    @Override
    public ItemListingDto updateItem(UUID itemId, ItemRequestDto itemDto) {

        Item item = findItemById(itemId);
        UUID userId = authFacade.getCurrentUserId();

        // Authorization Check
        if (!item.getOwner().getId().equals(userId)) {
            throw new SecurityException("User is not authorized to update this item.");
        }

        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setType(itemDto.getType());
        item.setForTrade(itemDto.getIsForTrade());
        item.setForDonation(itemDto.getIsForDonation());

        itemRepository.save(item);
        return new ItemListingDto(item);
    }

    //PRIVATE
    private List<Item> findOwnerItems(UUID ownerId) {
        return itemRepository.findByOwnerId(ownerId);
    }

    private List<Item> findOwnerItemsByStatus(UUID ownerId, ItemStatus status) {
        return itemRepository.findByOwnerIdAndStatus(ownerId, status);
    }
}