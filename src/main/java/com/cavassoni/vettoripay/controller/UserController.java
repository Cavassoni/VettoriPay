package com.cavassoni.vettoripay.controller;

import com.cavassoni.vettoripay.domain.mysql.dto.UserDto;
import com.cavassoni.vettoripay.domain.mysql.dto.UserPasswordDto;
import com.cavassoni.vettoripay.domain.mysql.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping(value = "/api/user")
public interface UserController {

    @GetMapping(value = "/{userId}")
    ResponseEntity<User> getUserById(@NotNull @PathVariable UUID userId);

    @PostMapping
    ResponseEntity<User> insert(@NotNull @RequestBody UserDto userDto);

    @PutMapping(value = "/{userId}")
    ResponseEntity<User> update(@NotNull @PathVariable UUID userId, @NotNull @RequestBody UserDto userDto);

    @PutMapping(value = "/{userId}/password")
    ResponseEntity<User> updatePassword(@NotNull @PathVariable UUID userId, @NotNull @RequestBody UserPasswordDto userPasswordDto);
}
