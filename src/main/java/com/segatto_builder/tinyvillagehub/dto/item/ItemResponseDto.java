package com.segatto_builder.tinyvillagehub.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
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
    private String type;
    private String imageUrl;
    private String status;
    private String ownerUsername;

}
