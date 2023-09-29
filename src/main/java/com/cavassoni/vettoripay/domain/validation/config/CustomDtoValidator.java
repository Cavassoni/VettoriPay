package com.cavassoni.vettoripay.domain.validation.config;

import com.cavassoni.vettoripay.domain.validation.dto.DtoValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomDtoValidator {
    Class<? extends DtoValidator> value();
}
