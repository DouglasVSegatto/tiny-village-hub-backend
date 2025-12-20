package com.segatto_builder.tinyvillagehub.dto.item;

import com.segatto_builder.tinyvillagehub.model.Item;
import com.segatto_builder.tinyvillagehub.model.enums.ItemType;
import com.segatto_builder.tinyvillagehub.model.enums.ItemStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class ItemResponseDto {

    /**
     * Example JSON:
     {
       "id": 1,
       "name": "Harry Potter Book",
       "description": "First edition Harry Potter book in good condition",
       "type": "BOOK",
       "imageUrl": "/uploads/1234567890_book.jpg",
       "status": "AVAILABLE",
       "ownerUsername": "user1"
     }
     */

    private UUID id;
    private String name;
    private String description;
    private ItemType type;
    private String imageUrl;
    private ItemStatus status;
    private String ownerUsername; // Display the owner's username, not their sensitive ID/email

    // Constructor to map the Item entity to the DTO
    public ItemResponseDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.type = item.getType();
        this.status = item.getStatus();
        // IMPORTANT: Safely get the owner's username
        this.ownerUsername = item.getOwner() != null ? item.getOwner().getUsername() : "Unknown";
    }

}
