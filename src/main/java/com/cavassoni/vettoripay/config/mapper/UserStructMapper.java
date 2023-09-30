package com.cavassoni.vettoripay.config.mapper;

import com.cavassoni.vettoripay.domain.mysql.dto.UserDto;
import com.cavassoni.vettoripay.domain.mysql.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserStructMapper {

    User convert(UserDto dto);

    @Mappings({
            @Mapping(target = "userType", ignore = true),
            @Mapping(target = "password", ignore = true),
    })
    User update(UserDto dto, @MappingTarget User userTelephone);
}
