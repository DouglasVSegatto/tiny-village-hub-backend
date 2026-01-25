package com.segatto_builder.tinyvillagehub.mappers;


import com.segatto_builder.tinyvillagehub.dto.user.UserRegistrationDto;
import com.segatto_builder.tinyvillagehub.dto.user.UserResponseDto;
import com.segatto_builder.tinyvillagehub.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toModel(UserRegistrationDto dto);

    @Mapping(source = "address.neighborhood", target = "neighborhood")
    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.state", target = "state")
    @Mapping(source = "address.country", target = "country")
    UserResponseDto toResponse(User user);
}
