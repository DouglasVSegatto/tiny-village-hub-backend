package com.segatto_builder.tinyvillagehub.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ItemServiceRequestDto {

    /**
     * DTO for communication with Item-Service microservice.
     * This includes owner information that is automatically populated from the authenticated user.
     *
     * Example JSON sent to Item-Service:
     {
     "name": "Harry Potter Book 1",
     "description": "Brand new Book",
     "type": "BOOK",
     "availabilityType": "TRADE",
     "condition": "NEW",
     "status":"ACTIVE",
     "ownerId": "550e8400-e29b-41d4-a716-446655440000",
     "ownerNeighbourhood": "Downtown",
     "ownerCity": "Seattle",
     "ownerState": "Washington",
     "ownerCountry": "USA"
     }
     */

    private String name;
    private String description;
    private String type;
    private String availabilityType;
    private String condition;
    private String status;

    // Owner information for microservice
    private UUID ownerId;
    private String ownerNeighbourhood;
    private String ownerCity;
    private String ownerState;
    private String ownerCountry;
}
