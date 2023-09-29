package com.cavassoni.vettoripay.controller.impl;

import com.cavassoni.vettoripay.domain.mysql.dto.UserDto;
import com.cavassoni.vettoripay.domain.mysql.entity.User;
import com.cavassoni.vettoripay.domain.mysql.type.UserType;
import com.cavassoni.vettoripay.service.models.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {UserControllerImpl.class})
@AutoConfigureMockMvc
public class UserControllerImplTest extends BaseControllerImplTest {

    private final String BASE_URL = "/api/user";

    @MockBean
    private UserService userService;

    @Test
    public void getUserById() throws Exception {
        final UUID userId = UUID.fromString("0a3bc262-cd98-41c0-9acf-901fed8ccfda");

        Mockito
                .when(userService.getUserById(userId))
                .thenReturn(Optional.of(User.builder().build()));

        mockMvc
                .perform(get(BASE_URL + "/" + userId))
                .andExpect(status().isOk());

        var argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        Mockito.verify(userService).getUserById(argumentCaptor.capture());

        assertEquals(userId, argumentCaptor.getValue());
    }

    @Test
    public void getUserByIdNotFound() throws Exception {
        final UUID userId = UUID.fromString("0a3bc262-cd98-41c0-9acf-901fed8ccfda");

        mockMvc
                .perform(get(BASE_URL + "/" + userId))
                .andExpect(status().isNotFound());

        var argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        Mockito.verify(userService).getUserById(argumentCaptor.capture());

        assertEquals(userId, argumentCaptor.getValue());
    }

    @Test
    public void insertUser() throws Exception {
        UserDto validUser = new UserDto("John Doe", "12578323485", "john@example.com", "27998874512", UserType.USER, "123456");

        mockMvc
                .perform(post(BASE_URL)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody(validUser)))
                .andExpect(status().isCreated());

        var argumentCaptor = ArgumentCaptor.forClass(UserDto.class);
        Mockito.verify(userService).insert(argumentCaptor.capture());

        Assertions.assertEquals(validUser, argumentCaptor.getValue());
    }

}