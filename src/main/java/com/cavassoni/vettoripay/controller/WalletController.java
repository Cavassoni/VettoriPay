package com.cavassoni.vettoripay.controller;

import com.cavassoni.vettoripay.domain.mysql.entity.Wallet;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping(value = "/api/wallet")
public interface WalletController {

    @GetMapping(value = "/{userId}")
    ResponseEntity<Wallet> getUserBalance(@NotNull @PathVariable UUID userId, @NotNull @RequestHeader("Authorization") String password);

}
