package com.cavassoni.vettoripay.controller.impl;

import com.cavassoni.vettoripay.controller.UserController;
import com.cavassoni.vettoripay.domain.mysql.dto.UserDto;
import com.cavassoni.vettoripay.domain.mysql.entity.User;
import com.cavassoni.vettoripay.service.models.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @Operation(
            summary = "Retorna um unico usuário através da chave enviada",
            tags = "User",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            }
    )
    public ResponseEntity<User> getUserById(@Parameter(description = "Id usuário") UUID userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Operation(
            summary = "Insere um novo usuário",
            tags = "User",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação"),
            }
    )
    public ResponseEntity<User> insert(UserDto userDto) {
        var newUser = userService.insert(userDto);
        return ResponseEntity //
                .status(HttpStatus.CREATED) //
                .body(newUser);
    }

    @Override
    public ResponseEntity<User> update(UUID userId, UserDto userDto) {
        var updatedUser = userService.update(userId, userDto);
        return ResponseEntity //
                .status(HttpStatus.OK) //
                .body(updatedUser);
    }
}
