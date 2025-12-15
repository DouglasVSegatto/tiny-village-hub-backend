package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.dto.item.ItemListingDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemUploadDto;
import com.segatto_builder.tinyvillagehub.model.Item;
import com.segatto_builder.tinyvillagehub.model.enums.ItemStatus;
import com.segatto_builder.tinyvillagehub.repository.ItemRepository;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final IAuthFacade authFacade;
    // ... other dependencies ...

    // ... existing constructors ...
//    @Value("${app.upload.dir}")
//    private String uploadDir;

    //USER RELATED - TODO improve as it goes.
    public List<ItemListingDto> findAllByUserId() {
        UUID userId = authFacade.getCurrentUserId();
        List<Item> items = findUserItems(userId);
        return items.stream()
                .map(ItemListingDto::new)
                .collect(Collectors.toList());
    }

    public List<Item> findActiveByUserId() {
        UUID userId = authFacade.getCurrentUserId();
        return findUserItemsByStatus(userId, ItemStatus.AVAILABLE);
    }

    //GENERAL
    public List<Item> findAllAvailableItems() {
        return itemRepository.findByStatus(ItemStatus.AVAILABLE);
    }

    public List<Item> findUserItems(UUID userId) {
        return itemRepository.findByOwnerId(userId);
    }

    public List<Item> findUserItemsByStatus(UUID uuid, ItemStatus status) {
        return itemRepository.findByOwnerIdAndStatus(uuid, status);
    }

    public Item findItemById(UUID itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with ID: " + itemId));
    }

    public ItemListingDto updateItem(UUID itemId, ItemUploadDto itemDto) throws IOException {

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

    public void deleteItem(UUID itemId) throws IOException {
        Item item = findItemById(itemId);
        UUID userId = authFacade.getCurrentUserId();
        // Authorization Check
        if (!item.getOwner().getId().equals(userId)) {
            throw new SecurityException("User is not authorized to delete this item.");
        }
        itemRepository.delete(item);
    }
}