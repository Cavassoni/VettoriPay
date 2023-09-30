package com.cavassoni.vettoripay.service.models.impl;

import com.cavassoni.vettoripay.config.exception.ValidationException;
import com.cavassoni.vettoripay.config.mapper.UserStructMapperImpl;
import com.cavassoni.vettoripay.domain.mysql.dto.UserDto;
import com.cavassoni.vettoripay.domain.mysql.dto.UserPasswordDto;
import com.cavassoni.vettoripay.domain.mysql.entity.User;
import com.cavassoni.vettoripay.domain.mysql.repository.UserRepository;
import com.cavassoni.vettoripay.domain.mysql.type.UserType;
import com.cavassoni.vettoripay.service.models.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig({UserServiceImpl.class, UserStructMapperImpl.class, BCryptPasswordEncoder.class})
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Mock
    private User mockedUser;

    @Test
    void getUserById() {
        final var userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockedUser));

        userService.getUserById(userId);

        verify(userRepository).findById(userId);
    }

    @Test
    void insert() {
        UserDto userDto = new UserDto("John Doe", "12578323485", "john@example.com", "27998874512", UserType.USER, "123456");

        when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(mockedUser);

        userService.insert(userDto);

        verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void update() {
        final var userId = UUID.randomUUID();
        UserDto userDto = new UserDto("John Doe", "12578323485", "john@example.com", "27998874512", UserType.USER, "123456");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(mockedUser));
        when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(mockedUser);

        userService.update(userId, userDto);

        verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void updatePassword() {
        final var userId = UUID.randomUUID();
        UserPasswordDto userPasswordDto = new UserPasswordDto("123456", "123123");

        var spiedUser = Mockito.spy(User.class);
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(spiedUser));
        when(spiedUser.getPassword()) //
                .thenReturn("$2a$10$ZW53b5x9PFhBdKXINh2efOsj2Ti.t9lzRsrZGyQK8np75zfFNARD2")
                .thenCallRealMethod();

        when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(spiedUser);

        userService.updatePassword(userId, userPasswordDto);

        final var argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());

        Assertions.assertTrue(passwordEncoder.matches(userPasswordDto.newPassword(), argumentCaptor.getValue().getPassword()));
    }

    @Test
    void updatePasswordErrorNotMatch() {
        final var userId = UUID.randomUUID();
        final var userPasswordDto = new UserPasswordDto("1234", "123123");

        var spiedUser = Mockito.spy(User.class);
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(spiedUser));
        when(spiedUser.getPassword()) //
                .thenReturn("$2a$10$ZW53b5x9PFhBdKXINh2efOsj2Ti.t9lzRsrZGyQK8np75zfFNARD2");

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> userService.updatePassword(userId, userPasswordDto));

        Assertions.assertEquals("Senha atual incorreta", exception.getMessage());

        verify(userRepository, never()).save(Mockito.any(User.class));
    }
}