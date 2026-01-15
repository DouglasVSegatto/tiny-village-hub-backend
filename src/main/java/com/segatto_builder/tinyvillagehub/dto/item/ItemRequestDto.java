package com.segatto_builder.tinyvillagehub.dto.item;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
     "condition": "NEW",
     "status":"ACTIVE"
     }
     */

    private String name;
    private String description;
    private String type;
    private String availabilityType;
    private String condition;
    private String status;
}
