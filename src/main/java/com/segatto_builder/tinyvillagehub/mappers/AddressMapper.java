package com.segatto_builder.tinyvillagehub.mappers;

import com.segatto_builder.tinyvillagehub.dto.user.AddressRequestDto;
import com.segatto_builder.tinyvillagehub.dto.user.UserRegistrationDto;
import com.segatto_builder.tinyvillagehub.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface AddressMapper {

    @Mapping(source = "neighborhood", target = "neighborhood")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "country", target = "country")
    Address fromRegistration(UserRegistrationDto dto);

    Address toModel(AddressRequestDto dto);


}
