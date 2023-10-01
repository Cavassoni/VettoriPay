package com.cavassoni.vettoripay.service.models.impl;

import com.cavassoni.vettoripay.config.exception.BadCredentials;
import com.cavassoni.vettoripay.config.exception.FindByIdNotFound;
import com.cavassoni.vettoripay.domain.mysql.entity.User;
import com.cavassoni.vettoripay.domain.mysql.entity.Wallet;
import com.cavassoni.vettoripay.domain.mysql.repository.WalletRepository;
import com.cavassoni.vettoripay.service.models.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig({WalletServiceImpl.class, BCryptPasswordEncoder.class})
class WalletServiceImplTest {

    @Autowired
    private WalletService walletService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private WalletRepository walletRepository;

    @Mock
    private Wallet mockedWallet;

    @Mock
    private User mockedUser;

    @Test
    void getUserBalance() {
        final var userId = UUID.randomUUID();
        final var password = "123456";

        Mockito.when(walletRepository.findByUser_Id(userId))
                .thenReturn(Optional.of(mockedWallet));

        Mockito.when(mockedWallet.getUser())
                .thenReturn(mockedUser);

        Mockito.when(mockedUser.getPassword())
                .thenReturn(passwordEncoder.encode(password));

        final var wallet = walletService.getUserBalance(userId, Base64.getEncoder().encodeToString(password.getBytes()));

        Assertions.assertNotNull(wallet);
    }

    @Test
    void getUserBalanceUserNotFound() {
        final var userId = UUID.randomUUID();
        final var password = Base64.getEncoder().encodeToString("123456".getBytes());

        Mockito.when(walletRepository.findByUser_Id(userId))
                .thenReturn(Optional.empty());

        final var notFound = assertThrows(FindByIdNotFound.class, () -> walletService.getUserBalance(userId, password));

        Assertions.assertEquals("Usuário não encontrado", notFound.getMessage());
    }


    @Test
    void getUserBalancePasswordNotMatch() {
        final var userId = UUID.randomUUID();

        Mockito.when(walletRepository.findByUser_Id(userId))
                .thenReturn(Optional.of(mockedWallet));

        Mockito.when(mockedWallet.getUser())
                .thenReturn(mockedUser);

        Mockito.when(mockedUser.getPassword())
                .thenReturn(passwordEncoder.encode("123456"));

        final var notFound = assertThrows(BadCredentials.class, () -> walletService.getUserBalance(userId, Base64.getEncoder().encodeToString("1234".getBytes())));

        Assertions.assertEquals("Senha inválida", notFound.getMessage());
    }

    @Test
    void getUserBalanceWithEmptyPassword() {
        final var userId = UUID.randomUUID();

        Mockito.when(walletRepository.findByUser_Id(userId))
                .thenReturn(Optional.of(mockedWallet));

        Mockito.when(mockedWallet.getUser())
                .thenReturn(mockedUser);

        Mockito.when(mockedUser.getPassword())
                .thenReturn(passwordEncoder.encode("123456"));

        final var notFound = assertThrows(BadCredentials.class, () -> walletService.getUserBalance(userId, null));

        Assertions.assertEquals("Senha inválida", notFound.getMessage());
    }
}