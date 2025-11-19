package com.segatto_builder.tinyvillagehub.dto.item;


import com.segatto_builder.tinyvillagehub.model.enums.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
// If you don't have these, you can delete the import and the annotations:
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ItemUploadDto {

    // @NotBlank(message = "Item name is required")
    private String name;

    // @NotBlank(message = "Description is required")
    private String description;

    // @NotNull(message = "Item type is required")
    private ItemType type; // ItemType is your custom Enum (e.g., BOOK, TOOL, FOOD)

    // @NotNull
    private Boolean isForTrade;

    // @NotNull
    private Boolean isForDonation;

}
