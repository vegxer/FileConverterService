package ru.itdt.converterService.validators;

public interface Validator<T> {
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean validate(T object);
}
