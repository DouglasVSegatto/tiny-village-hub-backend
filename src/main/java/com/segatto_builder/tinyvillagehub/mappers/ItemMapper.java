package com.segatto_builder.tinyvillagehub.mappers;

import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemServiceRequestDto;
import com.segatto_builder.tinyvillagehub.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    // Microservice Mappers
    ItemServiceRequestDto toServiceRequest(ItemRequestDto dto, User user);

    @AfterMapping
    default void enrichWithOwner(@MappingTarget ItemServiceRequestDto target, User user) {
        target.setOwnerId(user.getId());
        target.setOwnerNeighbourhood(user.getAddress().getNeighborhood());
        target.setOwnerCity(user.getAddress().getCity());
        target.setOwnerState(user.getAddress().getState());
        target.setOwnerCountry(user.getAddress().getCountry());
    }
}
