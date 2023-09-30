package com.cavassoni.vettoripay.domain.mysql.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserPasswordDto(@NotNull String oldPassword,
                              @NotNull String newPassword) {
}
