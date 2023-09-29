package com.cavassoni.vettoripay.domain.validation.dto;

import com.cavassoni.vettoripay.config.exception.ValidationException;
import com.cavassoni.vettoripay.domain.mysql.dto.UserDto;
import com.cavassoni.vettoripay.domain.mysql.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig({UserDtoValidator.class})
public class UserDtoValidatorTest {

    @Autowired
    private UserDtoValidator userDtoValidator;

    @MockBean
    private UserRepository userRepository;

    @Test
    void validateInsertRequiredFields() {
        final var userDto = UserDto.builder()
                .name("John Doe")
                .build();

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> userDtoValidator.validateInsert(userDto));

        Assertions.assertEquals("E-mail ou telefone devem ser informados", exception.getMessage());
    }

    @Test
    public void validateInsertEmailAlreadyExists() {
        final var userDto = UserDto.builder()
                .name("John Doe")
                .email("john@example.com")
                .build();

        Mockito.when(userRepository.existsByEmail(userDto.email())).thenReturn(true);

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> userDtoValidator.validateInsert(userDto));

        Assertions.assertEquals("E-mail já cadastrado", exception.getMessage());
    }

    @Test
    public void validateInsertPhoneAlreadyExists() {
        final var userDto = UserDto.builder()
                .name("John Doe")
                .phone("27998874512")
                .build();

        Mockito.when(userRepository.existsByPhone(userDto.phone())).thenReturn(true);

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> userDtoValidator.validateInsert(userDto));

        Assertions.assertEquals("Telefone já cadastrado", exception.getMessage());
    }

    @Test
    public void validateInsertCpfAlreadyExists() {
        final var userDto = UserDto.builder()
                .name("John Doe")
                .cpf("12578323485")
                .phone("27998874512")
                .build();

        Mockito.when(userRepository.existsByCpf(userDto.cpf())).thenReturn(true);

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> userDtoValidator.validateInsert(userDto));

        Assertions.assertEquals("CPF já cadastrado", exception.getMessage());
    }

    @Test
    public void validateInsertSuccess() {
        final var userDto = UserDto.builder()
                .name("John Doe")
                .email("john@example.com")
                .phone("27998874512")
                .cpf("12578323485")
                .build();

        userDtoValidator.validateInsert(userDto);

        Mockito.verify(userRepository).existsByEmail(userDto.email());
        Mockito.verify(userRepository).existsByPhone(userDto.phone());
        Mockito.verify(userRepository).existsByCpf(userDto.cpf());

    }
}