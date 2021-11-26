package ru.itdt.converterService.Validators;

public interface Validator<T> {
    boolean isValid(T object);
}
