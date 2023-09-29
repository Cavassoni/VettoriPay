package com.cavassoni.vettoripay.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class VettoriPayBeanConfig {
    private final ApplicationContext applicationContext;

    public <T> T getBeanByClass(Class<T> var1) {
        return applicationContext.getBean(var1);
    }

}
