package com.cavassoni.vettoripay.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Base64;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserWalletIntegrationTest extends BaseIntegrationControllerTest {

    private final String BASE_URL = "/api/wallet";

    @Test
    @Sql(statements = """
                INSERT INTO vettoriPay_test.user (id, cpf, email, name, password, phone, user_type)
                VALUES (uuid_to_bin('b972d28c-b482-4b2b-9a65-18b192eb7bf4'), '12578323485', 'john@example.com', 'John Doe', '$2a$10$ZW53b5x9PFhBdKXINh2efOsj2Ti.t9lzRsrZGyQK8np75zfFNARD2', '27998874512', 'USER');
                INSERT INTO vettoriPay_test.wallet (id, user_id, balance)
                VALUES (uuid_to_bin('8d4d303e-7493-49c8-82ae-5967c67c6093'), uuid_to_bin('b972d28c-b482-4b2b-9a65-18b192eb7bf4'), 10);
            """)
    public void getUserBalance() throws Exception {
        final UUID userId = UUID.fromString("b972d28c-b482-4b2b-9a65-18b192eb7bf4");

        mockMvc
                .perform(get(BASE_URL + "/" + userId)
                        .header("Authorization", Base64.getEncoder().encodeToString("123456".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("8d4d303e-7493-49c8-82ae-5967c67c6093"))
                .andExpect(jsonPath("$.balance").value(10.00));
    }

    @Test
    @Sql(statements = """
                INSERT INTO vettoriPay_test.user (id, cpf, email, name, password, phone, user_type)
                VALUES (uuid_to_bin('b972d28c-b482-4b2b-9a65-18b192eb7bf4'), '12578323485', 'john@example.com', 'John Doe', '$2a$10$ZW53b5x9PFhBdKXINh2efOsj2Ti.t9lzRsrZGyQK8np75zfFNARD2', '27998874512', 'USER');
                INSERT INTO vettoriPay_test.wallet (id, user_id, balance)
                VALUES (uuid_to_bin('8d4d303e-7493-49c8-82ae-5967c67c6093'), uuid_to_bin('b972d28c-b482-4b2b-9a65-18b192eb7bf4'), 10);
            """)
    public void getUserBalancePasswordNotMatch() throws Exception {
        final UUID userId = UUID.fromString("b972d28c-b482-4b2b-9a65-18b192eb7bf4");

        mockMvc
                .perform(get(BASE_URL + "/" + userId)
                        .header("Authorization", Base64.getEncoder().encodeToString("1234".getBytes())))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Senha inválida"));
    }


}