package com.segatto_builder.tinyvillagehub.mappers;

import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemServiceCreateDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemServiceUpdateDto;
import com.segatto_builder.tinyvillagehub.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    // Microservice Mappers
    ItemServiceCreateDto toServiceCreate(ItemRequestDto dto, User user);

    ItemServiceUpdateDto toServiceUpdate(ItemRequestDto dto);

    @AfterMapping
    default void enrichWithOwner(@MappingTarget ItemServiceCreateDto target, User user) {
        target.setOwnerId(user.getId());
        target.setOwnerUsername(user.getUsername());
        target.setOwnerNeighbourhood(user.getAddress().getNeighborhood());
        target.setOwnerCity(user.getAddress().getCity());
        target.setOwnerState(user.getAddress().getState());
        target.setOwnerCountry(user.getAddress().getCountry());
    }
}
