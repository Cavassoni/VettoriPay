package com.cavassoni.vettoripay.service.models.impl;

import com.cavassoni.vettoripay.config.exception.BadCredentials;
import com.cavassoni.vettoripay.config.exception.FindByIdNotFound;
import com.cavassoni.vettoripay.domain.mysql.entity.Wallet;
import com.cavassoni.vettoripay.domain.mysql.repository.WalletRepository;
import com.cavassoni.vettoripay.service.models.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Wallet getUserBalance(UUID userId, String password) {
        final var wallet = walletRepository.findByUser_Id(userId)
                .orElseThrow(() -> new FindByIdNotFound("Usuário não encontrado"));

        if (password == null || !passwordEncoder.matches(new String(Base64.getDecoder().decode(password)), wallet.getUser().getPassword()))
            throw new BadCredentials("Senha inválida");

        return wallet;
    }

}
