package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.dto.item.ItemUploadDto;
import com.segatto_builder.tinyvillagehub.model.Item;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.model.enums.ItemStatus; // <-- Ensure this is imported
// ... other imports ...
import com.segatto_builder.tinyvillagehub.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    // ... other dependencies ...

    // ... existing constructors ...
    @Value("${app.upload.dir}")
    private String uploadDir;

    /**
     * Finds all items that are currently marked as AVAILABLE.
     */
    public List<Item> findAllAvailableItems() {
        return itemRepository.findByStatus(ItemStatus.AVAILABLE);
    }
    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with ID: " + itemId));
    }

    public Item updateItem(Long itemId, User owner, ItemUploadDto itemDto, MultipartFile file) throws IOException {

        // 1. Verify Item Existence
        Item item = findItemById(itemId);

        // 2. Authorization Check (CRUCIAL)
        if (!item.getOwner().getId().equals(owner.getId())) {
            throw new SecurityException("User is not authorized to update this item.");
        }

        // 3. Update Item Details from DTO
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setType(itemDto.getType());
        item.setForTrade(itemDto.getIsForTrade());
        item.setForDonation(itemDto.getIsForDonation());
        // Status remains unchanged unless explicitly handled

        // 4. Handle File Replacement
        if (file != null && !file.isEmpty()) {

            // Delete old file if it exists
            if (item.getImageUrl() != null) {
                Path oldFilePath = Paths.get(uploadDir, item.getImageUrl().substring("/uploads/".length()));
                Files.deleteIfExists(oldFilePath);
            }

            // Save new file (reuse logic from createNewItem)
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, filename);
            Files.copy(file.getInputStream(), filePath);

            // Update item with new image URL
            item.setImageUrl("/uploads/" + filename);
        }

        // 5. Save and Return
        return itemRepository.save(item);
    }

    public void deleteItem(Long itemId, User owner) throws IOException {

        // 1. Verify Item Existence
        Item item = findItemById(itemId);

        // 2. Authorization Check (CRUCIAL)
        if (!item.getOwner().getId().equals(owner.getId())) {
            throw new SecurityException("User is not authorized to delete this item.");
        }

        // 3. Delete the associated file
        if (item.getImageUrl() != null) {
            Path filePath = Paths.get(uploadDir, item.getImageUrl().substring("/uploads/".length()));
            Files.deleteIfExists(filePath);
        }

        // 4. Delete the item from the database
        itemRepository.delete(item);
    }
}