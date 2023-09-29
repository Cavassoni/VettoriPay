package com.cavassoni.vettoripay.service.models;

import com.cavassoni.vettoripay.domain.mysql.dto.UserDto;
import com.cavassoni.vettoripay.domain.mysql.entity.User;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> getUserById(@NotNull UUID userId);

    User insert(UserDto userDto);

    User update(UUID userId, UserDto userDto);
}
