package com.cavassoni.vettoripay.service.models.impl;

import com.cavassoni.vettoripay.config.exception.FindByIdNotFound;
import com.cavassoni.vettoripay.config.exception.ValidationException;
import com.cavassoni.vettoripay.config.mapper.UserStructMapper;
import com.cavassoni.vettoripay.domain.mysql.dto.UserDto;
import com.cavassoni.vettoripay.domain.mysql.dto.UserPasswordDto;
import com.cavassoni.vettoripay.domain.mysql.entity.User;
import com.cavassoni.vettoripay.domain.mysql.entity.Wallet;
import com.cavassoni.vettoripay.domain.mysql.repository.UserRepository;
import com.cavassoni.vettoripay.domain.validation.config.CustomDtoValidator;
import com.cavassoni.vettoripay.domain.validation.dto.UserDtoValidator;
import com.cavassoni.vettoripay.service.models.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserStructMapper userStructMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    @CustomDtoValidator(UserDtoValidator.class)
    public User insert(UserDto userDto) {
        User userConverted = userStructMapper.convert(userDto);
        userConverted.setWallet(Wallet.builder() //
                .balance(ObjectUtils.defaultIfNull(userDto.initialBalance(), BigDecimal.ZERO)) //
                .user(userConverted) //
                .build());

        if (StringUtils.isNotBlank(userDto.password()))
            userConverted.setPassword(passwordEncoder.encode(userDto.password()));

        return userRepository.save(userConverted);
    }

    @Override
    public User update(UUID userId, UserDto userDto) {
        final var user = getUser(userId);

        return userRepository.save(userStructMapper.update(userDto, user));
    }

    private User getUser(UUID userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new FindByIdNotFound("Usuário não encontrado"));
    }

    @Override
    public void updatePassword(UUID userId, UserPasswordDto userPasswordDto) {
        final var user = getUser(userId);

        if (!passwordEncoder.matches(userPasswordDto.oldPassword(), user.getPassword()))
            throw new ValidationException("Senha atual incorreta");

        user.setPassword(passwordEncoder.encode(userPasswordDto.newPassword()));

        userRepository.save(user);
    }
}
