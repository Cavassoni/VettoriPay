package com.cavassoni.vettoripay.service.models;

import com.cavassoni.vettoripay.domain.mysql.entity.Wallet;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface WalletService {
    Wallet getUserBalance(@NotNull UUID userId, @NotNull String password);
}
