package ru.itdt.converterService.validators;

public interface Validator<T> {
    boolean validate(T object);
}
