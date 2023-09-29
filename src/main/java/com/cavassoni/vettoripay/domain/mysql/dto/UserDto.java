package com.cavassoni.vettoripay.domain.mysql.dto;

import com.cavassoni.vettoripay.domain.mysql.type.UserType;
import com.cavassoni.vettoripay.domain.validation.dto.DtoValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDto(@NotNull String name,
                      @NotNull String cpf,
                      @Email String email,
                      String phone,
                      @NotNull UserType userType,
                      String password) implements DtoValidator<UserDto> {
}
