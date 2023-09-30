package com.cavassoni.vettoripay.integration;

import com.cavassoni.vettoripay.domain.mysql.dto.UserDto;
import com.cavassoni.vettoripay.domain.mysql.dto.UserPasswordDto;
import com.cavassoni.vettoripay.domain.mysql.type.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIntegrationTest extends BaseIntegrationControllerTest {

    private final String BASE_URL = "/api/user";

    @Test
    @Sql(statements = """
                INSERT INTO vettoriPay_test.user (id, cpf, email, name, password, phone, user_type)
                VALUES (uuid_to_bin('b972d28c-b482-4b2b-9a65-18b192eb7bf4'), '12578323485', 'john@example.com', 'John Doe', '$2a$10$ZW53b5x9PFhBdKXINh2efOsj2Ti.t9lzRsrZGyQK8np75zfFNARD2', '27998874512', 'USER');
                INSERT INTO vettoriPay_test.wallet (id, user_id, balance)
                VALUES (uuid_to_bin('8d4d303e-7493-49c8-82ae-5967c67c6093'), uuid_to_bin('b972d28c-b482-4b2b-9a65-18b192eb7bf4'), 10);
            """)
    public void getUserById() throws Exception {
        final UUID userId = UUID.fromString("b972d28c-b482-4b2b-9a65-18b192eb7bf4");

        mockMvc
                .perform(get(BASE_URL + "/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("b972d28c-b482-4b2b-9a65-18b192eb7bf4"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.cpf").value("12578323485"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.phone").value("27998874512"))
                .andExpect(jsonPath("$.userType").value("USER"))
                .andExpect(jsonPath("$.wallet.id").value("8d4d303e-7493-49c8-82ae-5967c67c6093"))
                .andExpect(jsonPath("$.wallet.balance").value(10));
    }

    @Test
    public void getUserByIdNotFound() throws Exception {
        final UUID userId = UUID.fromString("0a3bc262-cd98-41c0-9acf-901fed8ccfda");

        mockMvc
                .perform(get(BASE_URL + "/" + userId))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void insertUser() throws Exception {
        UserDto validUser = new UserDto("John Doe", "12578323485", "john@example.com", "27998874512", UserType.USER, "123456", BigDecimal.TEN);

        mockMvc
                .perform(post(BASE_URL)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody(validUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.wallet.balance").value(10));
    }

    @Test
    public void insertUserErrorValidator() throws Exception {
        UserDto validUser = new UserDto("John Doe", "12578323485", null, null, UserType.USER, "123456", BigDecimal.ZERO);

        mockMvc
                .perform(post(BASE_URL)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody(validUser)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("E-mail ou telefone devem ser informados"));
    }

    @Test
    @Sql(statements = """
                INSERT INTO vettoriPay_test.user (id, cpf, email, name, password, phone, user_type)
                VALUES (uuid_to_bin('b972d28c-b482-4b2b-9a65-18b192eb7bf4'), '12578323485', 'john@example.com', 'John Doe', '$2a$10$ZW53b5x9PFhBdKXINh2efOsj2Ti.t9lzRsrZGyQK8np75zfFNARD2', '27998874512', 'USER');
                INSERT INTO vettoriPay_test.wallet (id, user_id, balance)
                VALUES (uuid_to_bin('8d4d303e-7493-49c8-82ae-5967c67c6093'), uuid_to_bin('b972d28c-b482-4b2b-9a65-18b192eb7bf4'), 10);
            """)
    public void updateUser() throws Exception {
        final UUID userId = UUID.fromString("b972d28c-b482-4b2b-9a65-18b192eb7bf4");

        UserDto validUser = new UserDto("John", "12312312345", "e@e.com", null, UserType.LOGIST, "111111", null);

        mockMvc
                .perform(put(BASE_URL + String.format("/%s", userId))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody(validUser)))
                .andExpect(status().isOk());

        mockMvc
                .perform(get(BASE_URL + "/" + userId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("b972d28c-b482-4b2b-9a65-18b192eb7bf4"))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.cpf").value("12312312345"))
                .andExpect(jsonPath("$.email").value("e@e.com"))
                .andExpect(jsonPath("$.phone").isEmpty())
                .andExpect(jsonPath("$.userType").value("USER"))
        ;
    }

    @Test
    public void updateUserErrorNotFound() throws Exception {
        UserDto validUser = new UserDto("John Doe", "12578323485", null, null, UserType.USER, "123456", null);

        mockMvc
                .perform(put(BASE_URL + String.format("/%s", UUID.fromString("b972d28c-b482-4b2b-9a65-18b192eb7bf4")))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody(validUser)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Usuário não encontrado"));
    }

    @Test
    @Sql(statements = """
                INSERT INTO vettoriPay_test.user (id, cpf, email, name, password, phone, user_type)
                VALUES (uuid_to_bin('b972d28c-b482-4b2b-9a65-18b192eb7bf4'), '12578323485', 'john@example.com', 'John Doe', '$2a$10$ZW53b5x9PFhBdKXINh2efOsj2Ti.t9lzRsrZGyQK8np75zfFNARD2', '27998874512', 'USER');
            """)
    public void updatePassword() throws Exception {
        final UUID userId = UUID.fromString("b972d28c-b482-4b2b-9a65-18b192eb7bf4");

        final var passwordDto = UserPasswordDto.builder()
                .oldPassword("123456")
                .newPassword("654321")
                .build();

        mockMvc
                .perform(put(BASE_URL + String.format("/%s/password", userId))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody(passwordDto)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(statements = """
                INSERT INTO vettoriPay_test.user (id, cpf, email, name, password, phone, user_type)
                VALUES (uuid_to_bin('b972d28c-b482-4b2b-9a65-18b192eb7bf4'), '12578323485', 'john@example.com', 'John Doe', '$2a$10$ZW53b5x9PFhBdKXINh2efOsj2Ti.t9lzRsrZGyQK8np75zfFNARD2', '27998874512', 'USER');
            """)
    public void updatePasswordNotMatch() throws Exception {
        final UUID userId = UUID.fromString("b972d28c-b482-4b2b-9a65-18b192eb7bf4");

        final var passwordDto = UserPasswordDto.builder()
                .oldPassword("123")
                .newPassword("654321")
                .build();

        mockMvc
                .perform(put(BASE_URL + String.format("/%s/password", userId))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody(passwordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Senha atual incorreta"));
    }
}