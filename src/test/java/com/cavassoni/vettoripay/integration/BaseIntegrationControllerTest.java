package com.cavassoni.vettoripay.integration;

import com.cavassoni.vettoripay.controller.impl.BaseControllerImplTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Transactional
@Rollback
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseIntegrationControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    public String requestBody(Object request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String loadResourceTestFile(String path) throws Exception {
        return FileUtils.readFileToString(new File(Objects.requireNonNull(BaseControllerImplTest.class.getClassLoader() //
                        .getResource(path)) //
                .getFile()), StandardCharsets.UTF_8);
    }

    public <T> T createObjectByResourceTestFile(String path, Class<T> object) throws Exception {
        return objectMapper.readValue(loadResourceTestFile(path), object);
    }
}
