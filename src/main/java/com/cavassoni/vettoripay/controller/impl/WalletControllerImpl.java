package com.cavassoni.vettoripay.controller.impl;

import com.cavassoni.vettoripay.controller.WalletController;
import com.cavassoni.vettoripay.domain.mysql.entity.Wallet;
import com.cavassoni.vettoripay.service.models.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WalletControllerImpl implements WalletController {

    private final WalletService walletService;

    @Override
    @Operation(
            summary = "Saldo do usuário. Necessário informar o ID do usuário e senha em base64, via header Authorization",
            tags = "User",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Saldo do usuário"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado com ID informado"),
            }
    )
    public ResponseEntity<Wallet> getUserBalance(@Parameter(description = "Id usuário") UUID userId,
                                                 @Parameter(description = "Senha em base64") String password) {
        Wallet wallet = walletService.getUserBalance(userId, password);
        return ResponseEntity.ok(wallet);
    }

}
