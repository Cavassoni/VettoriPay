package com.cavassoni.vettoripay.domain.validation.config;

import com.cavassoni.vettoripay.config.VettoriPayBeanConfig;
import com.cavassoni.vettoripay.domain.validation.dto.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Aspect
@Component
@RequiredArgsConstructor
public class ValidatorModelDtoAspect {

    private final VettoriPayBeanConfig beanConfig;

    @Before("""
            	execution(* com.cavassoni.vettoripay.service.models.impl.*.insert(..))
            && @annotation(com.cavassoni.vettoripay.domain.validation.config.CustomDtoValidator)
            && (args(dto,..))
            """)
    public void dtoValidateInsert(JoinPoint joinPoint, DtoValidator<?> dto) {
        final var validator = ((MethodSignature) joinPoint.getSignature()) //
                .getMethod() //
                .getAnnotation(CustomDtoValidator.class);

        final var dtoValidator = beanConfig.getBeanByClass(validator.value());
        dtoValidator.validateInsert(dto);
    }

    @Before("""
            	execution(* com.cavassoni.vettoripay.service.models.impl.*.update(..))
            && @annotation(com.cavassoni.vettoripay.domain.validation.config.CustomDtoValidator)
            && (args(dto,..))
            """)
    public void dtoValidateUpdate(JoinPoint joinPoint, DtoValidator<?> dto) {
        final var validator = ((MethodSignature) joinPoint.getSignature()) //
                .getMethod() //
                .getAnnotation(CustomDtoValidator.class);

        final var dtoValidator = beanConfig.getBeanByClass(validator.value());
        dtoValidator.validateUpdate(dto);
    }
}
