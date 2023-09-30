package com.cavassoni.vettoripay.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class VettoriPayBeanConfig {
    private final ApplicationContext applicationContext;

    public <T> T getBeanByClass(Class<T> var1) {
        return applicationContext.getBean(var1);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
