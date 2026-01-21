package com.segatto_builder.tinyvillagehub.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemServiceUpdateDto {

    /**
     * DTO for updating items in Item-Service microservice.
     * Does not include ownerId or status (those are managed separately).
     *
     * Example JSON:
     {
     "name": "Harry Potter Book 1 - Updated",
     "description": "Brand new Book - Updated description",
     "type": "BOOK",
     "availabilityType": "TRADE",
     "condition": "USED"
     }
     */

    private String name;
    private String description;
    private String type;
    private String availabilityType;
    private String condition;
}
