package com.cavassoni.vettoripay.domain.mysql.repository;

import com.cavassoni.vettoripay.domain.mysql.entity.User;
import com.cavassoni.vettoripay.domain.mysql.type.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.UUID;

public class UserRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql(statements = """
                INSERT INTO vettoriPay_test.user (id, cpf, email, name, password, phone, user_type)
                VALUES (uuid_to_bin('b972d28c-b482-4b2b-9a65-18b192eb7bf4'), '14875412587', 'email@email.com', 'User', '$2a$10$ZW53b5x9PFhBdKXINh2efOsj2Ti.t9lzRsrZGyQK8np75zfFNARD2', '27988874512', 'USER');
            """)
    public void findById() {
        Optional<User> optUser = userRepository.findById(UUID.fromString("b972d28c-b482-4b2b-9a65-18b192eb7bf4"));

        Assertions.assertTrue(optUser.isPresent());
        Assertions.assertEquals("User", optUser.get().getName());
    }

    @Test
    public void insert() {
        final var user = User.builder()
                .name("Teste")
                .cpf("12345678901")
                .email("email@test.com")
                .phone("12345678901")
                .userType(UserType.USER)
                .password("1234")
                .build();

        userRepository.save(user);
    }
}