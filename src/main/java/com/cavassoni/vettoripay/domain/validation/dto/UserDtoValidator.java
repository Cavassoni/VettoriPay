package com.cavassoni.vettoripay.domain.validation.dto;

import com.cavassoni.vettoripay.config.exception.ValidationException;
import com.cavassoni.vettoripay.domain.mysql.dto.UserDto;
import com.cavassoni.vettoripay.domain.mysql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoValidator implements DtoValidator<UserDto> {

    private final UserRepository userRepository;

    public void validateInsert(UserDto target) {
        if (ObjectUtils.allNull(target.email(), target.phone()))
            throw new ValidationException("E-mail ou telefone devem ser informados");

        if (userRepository.existsByEmail(target.email()))
            throw new ValidationException("E-mail já cadastrado");

        if (userRepository.existsByPhone(target.phone()))
            throw new ValidationException("Telefone já cadastrado");

        if(userRepository.existsByCpf(target.cpf()))
            throw new ValidationException("CPF já cadastrado");
    }
}
