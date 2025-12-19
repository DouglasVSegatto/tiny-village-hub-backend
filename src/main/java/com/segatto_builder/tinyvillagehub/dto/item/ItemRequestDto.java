package com.segatto_builder.tinyvillagehub.dto.item;


import com.segatto_builder.tinyvillagehub.model.enums.ItemAvailabilityType;
import com.segatto_builder.tinyvillagehub.model.enums.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// If you don't have these, you can delete the import and the annotations:
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ItemRequestDto {

    /**
     * Example JSON:
     {
     "name": "Harry Potter Book",
     "description": "First edition Harry Potter book in good condition",
     "type": "BOOK",
     "availabilityType": "TRADE"
     }
     */

    // @NotBlank(message = "Item name is required")
    private String name;

    // @NotBlank(message = "Description is required")
    private String description;

    // @NotNull(message = "Item type is required")
    private ItemType type; // ItemType is your custom Enum (e.g., BOOK, TOOL, FOOD)

    // @NotNull(message = "Item Availability type is required")
    private ItemAvailabilityType availabilityType;
}
