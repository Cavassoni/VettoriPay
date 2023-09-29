package com.cavassoni.vettoripay.service.models.impl;

import com.cavassoni.vettoripay.config.mapper.UserStructMapper;
import com.cavassoni.vettoripay.config.mapper.UserStructMapperImpl;
import com.cavassoni.vettoripay.domain.mysql.dto.UserDto;
import com.cavassoni.vettoripay.domain.mysql.entity.User;
import com.cavassoni.vettoripay.domain.mysql.repository.UserRepository;
import com.cavassoni.vettoripay.domain.mysql.type.UserType;
import com.cavassoni.vettoripay.service.models.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig({UserServiceImpl.class, UserStructMapperImpl.class})
class UserServiceImplTest {

    @Autowired
    private UserService userService;

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
}