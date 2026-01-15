package com.segatto_builder.tinyvillagehub.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemServiceResponseDto {

    /**
     * DTO for receiving responses from Item-Service microservice.
     * Uses String id to match Item-Service's 18-byte format.
     * 
     * Example JSON:
     {
       "id": "696881437e0814e3181e6aa1",
       "name": "Harry Potter Book 1",
       "description": "Brand new hardcover book in perfect condition",
       "type": "BOOK",
       "availabilityType": "TRADE",
       "condition": "NEW",
       "status": "ACTIVE",
       "ownerUsername": "john_doe",
       "ownerNeighbourhood": "Downtown",
       "ownerCity": "Seattle",
       "ownerState": "Washington",
       "ownerCountry": "USA",
       "imageUrls": ["https://pub-xxx.r2.dev/items/2026/696881437e0814e3181e6aa1/gallery_1.jpg"],
       "createdAt": "2026-01-15T20:30:00"
     }
     */

    private String id;
    private String name;
    private String description;
    private String type;
    private String availabilityType;
    private String condition;
    private List<String> imageUrls;
    private String status;
    private String ownerUsername;
    private String ownerNeighbourhood;
    private String ownerCity;
    private String ownerState;
    private String ownerCountry;
    private String createdAt;
}
