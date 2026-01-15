package com.segatto_builder.tinyvillagehub.dto.item;


import com.segatto_builder.tinyvillagehub.model.enums.ItemAvailabilityType;
import com.segatto_builder.tinyvillagehub.model.enums.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ItemRequestDto {

    /**
     * Example JSON:
     {
     "name": "Harry Potter Book 1",
     "description": "Brand new Book",
     "type": "BOOK",
     "availabilityType": "TRADE",
     "condition": "NEW"
     }
     */

    private String name;
    private String description;
    private ItemType type;
    private ItemAvailabilityType availabilityType;
    private String condition;
    private String status;
}
