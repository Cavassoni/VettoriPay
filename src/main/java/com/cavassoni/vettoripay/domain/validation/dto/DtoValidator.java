package com.cavassoni.vettoripay.domain.validation.dto;

public interface DtoValidator<T> {

    default void validateInsert(T target) {
    }

    default void validateUpdate(T target) {
    }
}
